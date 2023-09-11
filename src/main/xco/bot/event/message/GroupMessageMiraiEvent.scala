package xco.bot.event.message

import net.mamoe.mirai.contact.MemberKt
import net.mamoe.mirai.event.events.{GroupMessageEvent, MessageEvent}
import net.mamoe.mirai.message.data.*
import xco.bot.event.OnGroup

import scala.collection.ArrayOps

case class GroupMessageMiraiEvent(event: GroupMessageEvent) extends MessageMiraiEvent(event), OnGroup(event.getGroup) {
	
	override def parseAt(at: At): String | Null = super.getNameCardOrNick(at.getTarget)

}