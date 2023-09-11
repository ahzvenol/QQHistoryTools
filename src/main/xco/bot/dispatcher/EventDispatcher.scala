package xco.bot.dispatcher

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.events.{GroupMessageEvent, MessageEvent}
import net.mamoe.mirai.event.{Event, EventHandler}
import net.mamoe.mirai.message.data.MessageChain
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Lazy
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import xco.bot.annotation.Listen
import xco.bot.config.EmptyBot
import xco.bot.dispatcher.ListenerProcessor.eventMap
import xco.common.util.FPUtil.|>

import java.lang.reflect.{InvocationTargetException, Method}
import javax.annotation.PostConstruct
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

@Component
class EventDispatcher(val bot: Bot) {

  if bot.isInstanceOf[EmptyBot]
  then LoggerFactory.getLogger("xco.bot.EventDispatcher").warn("Bot初始化失败,已屏蔽Bot模块")
  else bot.getEventChannel.subscribeAlways(classOf[Event], e => onMessage(e.asInstanceOf[Event]))

  private def onMessage(event: Event): Unit = {
    //    println(eventMap)
    //		println()
    dispatch(event)
    //		println()
    //    println(event)
    //    println(event.getClass)
  }

  private def dispatch(event: Event): Unit = {
    for (eventClass, callbackList) <- eventMap
        if eventClass.isAssignableFrom(event.getClass)
        callback <- callbackList
    do try callback(event)
    catch case e: InvocationTargetException => e.getTargetException.printStackTrace()
  }
}
