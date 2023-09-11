package xco.bot.event

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.Event
import net.mamoe.mirai.message.data.MessageContent
import xco.common.util.BeanUtil

import java.util.Date

trait AbstractMiraiEvent {
	val senderId: Long
	val senderName: String
	val timeStamp: Date
	val source: Int
	val quote: Int | Null
	lazy val messageList: List[Map[String, Any]]
}

object AbstractMiraiEvent {
	val bot: Bot = BeanUtil.getBean(classOf[Bot]).nn
}