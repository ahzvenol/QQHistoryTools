package xco.bot.event.message

import net.mamoe.mirai.event.events.{FriendMessageEvent, GroupTempMessageEvent, MessageEvent, StrangerMessageEvent}
import net.mamoe.mirai.message.data.{At, MessageContent}

case class PrivateMessageMiraiEvent(event: FriendMessageEvent | GroupTempMessageEvent | StrangerMessageEvent) extends MessageMiraiEvent(event) {
	
	override def parseAt(at: At): String | Null = null
	
}
