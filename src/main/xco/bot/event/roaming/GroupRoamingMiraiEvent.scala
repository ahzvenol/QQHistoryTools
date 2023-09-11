package xco.bot.event.roaming

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.{At, MessageChain}
import xco.bot.event.OnGroup
import xco.bot.event.roaming.GroupRoamingMiraiEvent.GroupRoamingMessage

case class GroupRoamingMiraiEvent(private val groupRoamingMessage: GroupRoamingMessage)
	extends RoamingMiraiEvent(groupRoamingMessage.message), OnGroup(groupRoamingMessage.group) {
	override val senderName: String = super.getNameCardOrNick(this.senderId)

	override def parseAt(at: At): String = super.getNameCardOrNick(at.getTarget)
}

object GroupRoamingMiraiEvent {
	case class GroupRoamingMessage(group: Group, message: MessageChain)
}