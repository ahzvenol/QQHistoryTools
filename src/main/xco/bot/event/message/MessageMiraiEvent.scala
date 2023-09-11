package xco.bot.event.message

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.*
import xco.bot.event.{AbstractMiraiEvent, HaveMessage}
import xco.common.util.{BeanUtil, DateUtil}

import java.util.Date
import scala.collection.ArrayOps

abstract class MessageMiraiEvent(event: MessageEvent) extends AbstractMiraiEvent, HaveMessage(event.getMessage) {
	override val senderId: Long = event.getSender.getId
	override val senderName: String = event.getSenderName
	override val timeStamp: Date = DateUtil.fromInt(event.getTime)
	override val source: Int = message.get(MessageSource.Key).getIds.toList.head
	override val quote: Int | Null = Option(message.get(QuoteReply.Key)).map(_.nn.getSource.nn.getIds()(0)).orNull
}