package xco.record.service.impl

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall
import net.mamoe.mirai.event.events.{GroupMessageEvent, MessageRecallEvent}
import net.mamoe.mirai.message.data.*
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import xco.bot.event.message.{GroupMessageMiraiEvent, NodeMessageMiraiEvent}
import xco.bot.event.nudge.GroupNudgeMiraiEvent
import xco.bot.event.nudge.GroupNudgeMiraiEvent.GroupNudgeEvent
import xco.bot.event.recall.GroupRecallMiraiEvent
import xco.bot.event.roaming.GroupRoamingMiraiEvent
import xco.bot.event.roaming.GroupRoamingMiraiEvent.GroupRoamingMessage
import xco.bot.event.{AbstractMiraiEvent, HaveMessage}
import xco.common.bot.StatusMonitor
import xco.common.util.FPUtil.|>
import xco.common.util.{DateUtil, ExceptionUtil, MD5Util, MailSendUtil}
import xco.record.entity.{Msg, Recall}
import xco.record.mapper.{MsgSaveMapper, RecallMapper}
import xco.record.service.RecordService
import xco.record.util.{DownloadUtil, TimeUtil}

import java.io.File
import java.text.SimpleDateFormat
import java.util
import java.util.{Date, HashMap}
import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`

@Service
class RecordServiceImpl(bot: Bot, msgMapper: MsgSaveMapper, recallMapper: RecallMapper) extends RecordService {

  //	解析器的ID是Code,数据库存储的是member表对应的ID
  private def getMemberId(qqCode: Long): Integer = {
    msgMapper.selectMemberIdByQQCode(qqCode) match {
      case id: Integer => id
      case null =>
        val map = new util.HashMap[String, Any]
        map.put("qqCode", qqCode)
        msgMapper.insertMember(map)
        map.get("id").asInstanceOf[Integer]
    }
  }

  private val root = System.getProperty("user.dir")

  private trait SaveMessage extends HaveMessage {
    this: AbstractMiraiEvent =>

    override def parseMessage(messageContent: MessageContent): Map[String, Any] = {
      def download(url: String, savePath: String, fileName: String): Unit = {
        if !File(savePath + fileName).exists then DownloadUtil.downLoadFromUrl(url, fileName, savePath)
      }
      //UNKNOWN类型图片直接原样保留
      messageContent match {
        case i: Image =>
          val md5 = MD5Util.bytesToHex(i.getMd5)
          download(s"http://gchat.qpic.cn/gchatpic_new//--$md5/0", root + "/image/", md5)
        case i: FlashImage =>
          val image = i.getImage
          val md5 = MD5Util.bytesToHex(image.getMd5)
          download(s"http://gchat.qpic.cn/gchatpic_new//--$md5/0", root + "/image/", md5)
        case a: OnlineAudio =>
          val md5 = MD5Util.bytesToHex(a.getFileMd5)
          download(a.getUrlForDownload, root + "/audio/", md5 + '.' + a.getCodec.getFormatName)
        case f: ForwardMessage => for node <- f.getNodeList do (new NodeMessageMiraiEvent(node) with SaveMessage).messageList
        case _ =>
      }
      super.parseMessage(messageContent)
    }
  }

  private var (groupMsgCount, groupRecallCount, exceptionCount) = (0, 0, 0)

  @Scheduled(cron = "0 0 0 1/1 * ? ")
  //@Scheduled(cron = "0 0/5 * * * ? ")
  private def task(): Unit = {
    if bot.isOnline
    then
      val title = SimpleDateFormat("yyyy年MM月dd日").format(Date()) + " bot运行正常";
      val content =
        s"""
           |现在时间是：${SimpleDateFormat("HH:mm:ss").format(Date())},
           |已运行：${TimeUtil.getTimeInterval(StatusMonitor.startTime)},
           |今日聊天记录条数：$groupMsgCount,
           |撤回条数：$groupRecallCount,
           |错误数:$exceptionCount""".stripMargin.replaceAll("\n", "")

      MailSendUtil.sendEmail(title, content)
    else MailSendUtil.sendEmail(SimpleDateFormat("yyyy年MM月dd日").format(Date()) + " bot已离线", "")
    
    groupMsgCount = 0
    groupRecallCount = 0
    exceptionCount = 0
  }

  private def reportException(e: Throwable): Unit = {
    exceptionCount += 1
    MailSendUtil.sendEmail(s"botError:${e.getClass.toString}", ExceptionUtil.toString(e))
  }

  override def saveGroupMessage(event: GroupMessageEvent): Unit = new GroupMessageMiraiEvent(event) with SaveMessage |> saveMessage

  override def saveGroupNudge(event: GroupNudgeEvent): Unit = GroupNudgeMiraiEvent(event) |> saveMessage

  override def saveGroupRoaming(message: GroupRoamingMessage): Unit = GroupRoamingMiraiEvent(message) |> saveMessage

  private def saveMessage(event: AbstractMiraiEvent): Unit = {
    groupMsgCount += 1
    try
      Msg().setSource(event.source)
        .setQuote(if (event.quote != null) event.quote.nn else null)
        .setTimeStamp(event.timeStamp)
        .setSenderId(getMemberId(event.senderId))
        .setSenderName(event.senderName)
        .setMsg(Serialization.write(event.messageList)(using DefaultFormats))
        |> msgMapper.insert
    catch case e: Throwable => reportException(e)
  }

  override def saveGroupRecall(event: GroupRecallMiraiEvent): Unit = {
    groupRecallCount += 1
    try
      Recall().setRecallSource(event.source)
        .setTimeStamp(event.timeStamp)
        .setAuthorId(getMemberId(event.authorId))
        .setAuthorName(event.authorName)
        .setOperatorId(if (event.authorId != event.operatorId) getMemberId(event.operatorId) else null)
        .setOperatorName(if (event.authorId != event.operatorId) event.operatorName else null)
        |> recallMapper.insert
    catch case e: Throwable => reportException(e)
  }

}