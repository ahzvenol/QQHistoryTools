package xco.record.listener

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall
import net.mamoe.mirai.event.events.{GroupMessageEvent, NudgeEvent}
import org.springframework.stereotype.Component
import xco.bot.annotation.Listen
import xco.bot.event.nudge.GroupNudgeMiraiEvent.GroupNudgeEvent
import xco.bot.event.recall.GroupRecallMiraiEvent
import xco.bot.event.roaming.GroupRoamingMiraiEvent
import xco.common.util.FPUtil.|>
import xco.record.config.RecordConfig
import xco.record.service.RecordService

import java.util
import java.util.stream.Collectors
import java.util.{Timer, TimerTask}
import javax.annotation.PostConstruct
import scala.collection.mutable.*


@Component
class RecordListener(config: RecordConfig, recordService: RecordService) {

	@Listen(Array(classOf[GroupMessageEvent]))
	def listenMessage(event: GroupMessageEvent): Unit = {
		if event.getGroup.getId != config.recordGroup then return;
		recordService.saveGroupMessage(event)
	}

	@Listen(Array(classOf[NudgeEvent]))
	def listenNudge(event: NudgeEvent): Unit = {
		if !event.getSubject.isInstanceOf[Group] ||
			event.getSubject.getId != config.recordGroup then return;
		recordService.saveGroupNudge(event.asInstanceOf[GroupNudgeEvent])
	}

	@Listen(Array(classOf[GroupRecall]))
	def listenRecall(event: GroupRecall): Unit = {
		if event.getGroup.getId != config.recordGroup then return;
		recordService.saveGroupRecall(GroupRecallMiraiEvent(event))
	}

	//	todo:记录bot自己的发言
}