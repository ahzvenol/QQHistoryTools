package xco.bot.event.roaming

import net.mamoe.mirai.message.data.{MessageChain, MessageSource, QuoteReply}
import xco.bot.event.{AbstractMiraiEvent, HaveMessage}
import xco.common.util.DateUtil

import java.util.Date

abstract class RoamingMiraiEvent(message: MessageChain) extends AbstractMiraiEvent, HaveMessage(message) {
	private val messageSource: MessageSource = message.get(MessageSource.Key)
	override val senderId: Long = messageSource.getFromId
	override val timeStamp: Date = DateUtil.fromInt(messageSource.getTime)
	override val source: Int = messageSource.getIds.toList.head
	override val quote: Int | Null = Option(message.get(QuoteReply.Key)).map(_.nn.getSource.nn.getIds()(0)).orNull
}
