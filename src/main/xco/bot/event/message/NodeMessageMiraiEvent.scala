package xco.bot.event.message

import net.mamoe.mirai.message.data.{At, ForwardMessage, MessageContent}
import xco.bot.event.{AbstractMiraiEvent, HaveMessage}
import xco.common.util.DateUtil

import java.util.Date

case class NodeMessageMiraiEvent(node: ForwardMessage.Node) extends AbstractMiraiEvent, HaveMessage(node.getMessageChain) {
	override val senderId: Long = node.getSenderId
	override val senderName: String = node.getSenderName
	override val timeStamp: Date = DateUtil.fromInt(node.getTime)
	override val source: Int = -2
	override val quote: Int | Null = null
	
	override def parseAt(at: At): String | Null = null
}
