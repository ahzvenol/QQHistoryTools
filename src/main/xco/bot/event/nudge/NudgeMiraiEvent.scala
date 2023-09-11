package xco.bot.event.nudge

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.event.events.NudgeEvent
import net.mamoe.mirai.message.data.MessageContent
import xco.bot.event.AbstractMiraiEvent
import xco.common.util.FPUtil.|>

import java.util.Date

abstract class NudgeMiraiEvent(event: NudgeEvent) extends AbstractMiraiEvent {
	override val senderId: Long = event.getFrom.getId
	override val senderName: String = event.getFrom.getNick
	//	tag:不精确的时间
	override val timeStamp: Date = Date()
	override val source: Int = -1
	override val quote: Int | Null = null
	//	tag 可能需要存下id,但是有点冗余?
	override lazy val messageList: List[Map[String, Any]] =
		Map("senderName" -> this.event.getFrom.getNick,
			"senderId" -> this.event.getFrom.getId,
			"targetName" -> this.event.getTarget.getNick,
			"targetId" -> this.event.getTarget.getId,
			"action" -> this.event.getAction,
			"suffix" -> this.event.getSuffix
		) |> (e => Map("nudge" -> e)) |> List().appended
}
