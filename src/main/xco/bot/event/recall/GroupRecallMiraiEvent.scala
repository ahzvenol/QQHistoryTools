package xco.bot.event.recall

import net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall
import xco.bot.event.OnGroup
import xco.bot.event.recall.RecallMiraiEvent

case class GroupRecallMiraiEvent(event: GroupRecall) extends RecallMiraiEvent(event), OnGroup(event.getGroup) {
	override val authorName: String = super.getNameCardOrNick(event.getAuthor.getId)
	override val operatorId: Long = event.getOperator.getId
	override lazy val operatorName: String = super.getNameCardOrNick(event.getOperator.getId)
}
