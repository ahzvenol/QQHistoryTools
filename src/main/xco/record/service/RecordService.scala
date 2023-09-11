package xco.record.service

import net.mamoe.mirai.event.events.GroupMessageEvent
import xco.bot.event.nudge.GroupNudgeMiraiEvent.GroupNudgeEvent
import xco.bot.event.recall.GroupRecallMiraiEvent
import xco.bot.event.roaming.GroupRoamingMiraiEvent.GroupRoamingMessage

trait RecordService {

	def saveGroupMessage(event: GroupMessageEvent): Unit

	def saveGroupNudge(event: GroupNudgeEvent): Unit

	def saveGroupRoaming(message: GroupRoamingMessage): Unit

	def saveGroupRecall(parser: GroupRecallMiraiEvent): Unit
}
