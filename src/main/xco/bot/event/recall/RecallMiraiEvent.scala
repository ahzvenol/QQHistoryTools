package xco.bot.event.recall

import net.mamoe.mirai.event.events.MessageRecallEvent
import xco.common.util.DateUtil

import java.util.Date

abstract class RecallMiraiEvent(event: MessageRecallEvent) {
	val authorId: Long = event.getAuthorId
	val authorName: String = event.getAuthor.getNick
	val timeStamp: Date = DateUtil.fromInt(event.getMessageTime)
	val source: Int = event.getMessageIds.toList.head
	val operatorId: Long
	lazy val operatorName: String
}
