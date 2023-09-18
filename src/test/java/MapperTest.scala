import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import xco.Application
import xco.handler.AndroidTableNameHandler
import xco.handler.AndroidTableNameHandler.AndroidTableQueryInfo
import xco.mapper.AndroidDatabaseMapperWrapper
import xco.util.FPUtil.|>
import scala.util.Using
import java.io.*


@RunWith(classOf[SpringRunner])
@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[Application]))
class MapperTest() {
  @Autowired
  val androidDatabaseMapper: AndroidDatabaseMapperWrapper = null

  @Test
  def androidMessageListTraverse(): Unit = {
    import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`
    val targetType: "troop" | "friend" = "troop"
    val targetNumber = "904516937"
    val result = androidDatabaseMapper.usingMapper(AndroidTableQueryInfo(targetType, targetNumber))(_.selectList(null))
    for i <- result.toList do
      //      println(i)
      println(Using(ObjectInputStream(ByteArrayInputStream(i.getExtra))) { oi => oi.readObject() })
      println(String(i.getSenderId, "UTF-8"))
      println(String(i.getMsgData, "UTF-8"))
      println("------------")
  }
}
