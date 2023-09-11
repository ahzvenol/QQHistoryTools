package xco.bot.event

import net.mamoe.mirai.message.data.*
import org.json4s.DefaultFormats
import org.json4s.jackson.{Serialization, parseJson}
import xco.bot.event.AbstractMiraiEvent
import xco.bot.event.message.NodeMessageMiraiEvent
import xco.common.util.FPUtil.|>
import xco.common.util.{DateUtil, JsonUtil, MD5Util}
import xco.visualize.entity.CompleteMsgVO

import java.time.LocalDateTime
import java.util.Date
import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`
import scala.collection.{ArrayOps, WithFilter}
import scala.util.Try

trait HaveMessage(val message: MessageChain) {
	this: AbstractMiraiEvent =>

	//	nodeMessage不存在messageSource

	lazy val text: String = message.toArray.filter(_.isInstanceOf[PlainText]).foldLeft("")((a, b) => a + b.asInstanceOf[PlainText].getContent)

	override lazy val messageList: List[Map[String, Any]] = {
		this.message
			.withFilter(_.isInstanceOf[MessageContent])
			.map(_.asInstanceOf[MessageContent])
			.map(parseMessage)
			.toList
	}

	def parseAt(at: At): String | Null

	def parseMessage(messageContent: MessageContent): Map[String, Any] = messageContent match {
		case t: PlainText => Map("text" -> t.getContent)
		case i: Image => Map("image" ->
			Map("url" -> MD5Util.bytesToHex(i.getMd5), "isEmoji" -> i.isEmoji, "imageType" -> i.getImageType.getFormatName))
		case i: FlashImage => Map("flash" ->
			Map("url" -> MD5Util.bytesToHex(i.getImage.getMd5), "isEmoji" -> i.getImage.isEmoji, "imageType" -> i.getImage.getImageType.getFormatName))
		case a: At => Map("at" ->
			Map("atName" -> this.parseAt(a), "code" -> a.getTarget))
		case _: AtAll => Map("at" ->
			Map("atName" -> "全体成员", "code" -> "all"))
		case f: Face => Map("face" -> f.getId)
		case a: Audio => Map("voice" -> MD5Util.bytesToHex(a.getFileMd5), "audioType" -> a.getCodec.getFormatName)
		case m: MarketFace => Map("marketFace" ->
			Map("id" -> m.getId, "name" -> m.getName))
		case f: ForwardMessage =>
			val list = for node <- f.getNodeList; nodeParser = NodeMessageMiraiEvent(node)
				yield CompleteMsgVO()
					.setQqCode(nodeParser.senderId.toString)
					.setSenderName(nodeParser.senderName)
					.setMsgSendTime(nodeParser.timeStamp)
					.setMsgContent(Serialization.write(nodeParser.messageList)(using DefaultFormats))
					|> JsonUtil.toJson |> (e => parseJson(e))
			Map("forward" ->
				Map("title" -> f.getTitle, "preview" -> f.getPreview, "summary" -> f.getSummary, "content" -> list))
		case f: FileMessage => Map("file" ->
			Map("name" -> f.getName, "id" -> f.getId, "size" -> f.getSize))
		case p: PokeMessage => Map("poke" ->
			Map("id" -> p.getId, "name" -> p.getName))
		case v: VipFace => Map("vipFace" ->
			Map("id" -> v.getKind.getId, "name" -> v.getKind.getName))
		case l: LightApp => Map("lightApp" -> parseJson(l.contentToString))
		case m: MusicShare => Map("musicShare" ->
			Map("title" -> m.getTitle, "summary" -> m.getSummary, "jumpUrl" -> m.getJumpUrl,
				"pictureUrl" -> m.getPictureUrl, "musicUrl" -> m.getMusicUrl,
				"appId" -> m.getKind.getAppId, "packageName" -> m.getKind.getPackageName,
				"signature" -> m.getKind.getSignature))
		case unknown: MessageContent => Map("unknown" -> Try(parseJson(unknown.contentToString)).getOrElse(unknown.contentToString))
	}
}