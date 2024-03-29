import com.alibaba.fastjson.JSON
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import xco.Application
import xco.handler.AndroidTableNameHandler
import xco.handler.AndroidTableNameHandler.AndroidMessageTableQueryInfo
import xco.mapper.AndroidMessageMapperWrapper
import xco.mapper.WindowsMessageMapper
import xco.message.AbstractWindowsMessage
import xco.util.FPUtil.|>
import xco.util.TLVUtil
import xco.util.TLVUtil.TLV

import scala.util.{Try, Using}
import java.io.*
import javax.xml.bind.DatatypeConverter
import scala.collection.mutable.ListBuffer


@RunWith(classOf[SpringRunner])
@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[Application]))
class MapperTest() {
  @Autowired
  val androidMessageMapper: AndroidMessageMapperWrapper = null

  @Autowired
  val windowsMessageMapper: WindowsMessageMapper = null

  @Test
  def androidMessageListTraverse(): Unit = {
    import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`
    val targetType: "troop" | "friend" = "troop"
    val targetNumber = "904516937"
    val result = androidMessageMapper.usingMapper(AndroidMessageTableQueryInfo(targetType, targetNumber))(_.selectList(null))
    for i <- result.toList do
      //      println(i)
      Try(JSON.parse(String(i.getExtra, "UTF-8"))).foreach(println)
      println(String(i.getSenderId, "UTF-8"))
      println(i.getTime)
      println(String(i.getMsgData, "UTF-8"))
      println("------------")
  }

  @Test
  def windowsMessageListTraverse(): Unit = {
    import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`
    val targetType: "group" | "buddy" = "group"
    val targetNumber = 904516937
    val result = windowsMessageMapper.selectList(targetType, targetNumber)

    for i <- result.toList do {
      //      println(i.getId)
      //      println(i.getSenderId)
      //      println(i.getTime)
      //      println(DatatypeConverter.printHexBinary(i.getMsgContent))
      val absMessage = AbstractWindowsMessage(i)
      println(absMessage)
      println(absMessage.id)
      println(absMessage.senderId)
      println(absMessage.fontName)
      println(absMessage.timeStamp)
      println(absMessage.messageChain)

      println("------------")
    }
  }
}
