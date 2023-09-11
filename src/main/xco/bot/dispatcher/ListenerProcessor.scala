package xco.bot.dispatcher

import net.mamoe.mirai.event.Event
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import xco.bot.annotation.Listen
import xco.bot.dispatcher.ListenerProcessor.eventMap
import xco.common.util.FPUtil.|>

import java.lang.reflect.Method
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

@Component
class ListenerProcessor extends BeanPostProcessor {
	//tag:考虑直接根据parameter和是否包含注解判断?
	override def postProcessAfterInitialization(bean: AnyRef, beanName: String): AnyRef = {
		for method <- ReflectionUtils.getAllDeclaredMethods(bean.getClass).asInstanceOf[Array[Method]]
				parameters = method.getParameterTypes.asInstanceOf[Array[Class[?]]]
				if parameters.length == 1
				annotation <- AnnotationUtils.findAnnotation(method, classOf[Listen]) |> Option.apply
				eventClass <- annotation.nn.value.asInstanceOf[Array[Class[? <: Event]]].toList
				if parameters(0) == eventClass || parameters(0).isAssignableFrom(eventClass)
		do eventMap.getOrElseUpdate(eventClass, ListBuffer()) += (method.invoke(bean, _))
		bean
	}
}

object ListenerProcessor {
	val eventMap: mutable.Map[Class[? <: Event], ListBuffer[Event => Unit]] = mutable.Map()
}