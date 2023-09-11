package xco.bot.event.nudge

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.event.events.{GroupMessageEvent, NudgeEvent}
import net.mamoe.mirai.message.data.{MessageContent, MessageSource}
import xco.bot.event.OnGroup
import xco.bot.event.nudge.GroupNudgeMiraiEvent.GroupNudgeEvent
import xco.common.util.FPUtil.|>

case class GroupNudgeMiraiEvent(event: GroupNudgeEvent)
	extends NudgeMiraiEvent(event), OnGroup(event.getSubject.asInstanceOf[Group]) {

	override lazy val messageList: List[Map[String, Any]] =
		Map("senderName" -> super.getNameCardOrNick(event.getFrom.getId),
			"senderId" -> event.getFrom.getId,
			"targetName" -> super.getNameCardOrNick(event.getTarget.getId),
			"targetId" -> event.getTarget.getId,
			"action" -> event.getAction,
			"suffix" -> event.getSuffix
		) |> (e => Map("nudge" -> e)) |> List().appended
}

object GroupNudgeMiraiEvent{
	type GroupNudgeEvent = NudgeEvent {val subject: Group}
}