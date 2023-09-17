import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import xco.Application
import xco.mapper.AndroidDatabaseMapper
import xco.util.FPUtil.|>


@RunWith(classOf[SpringRunner])
@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[Application]))
class MapperTest() {
  @Autowired
  val androidDatabaseMapper: AndroidDatabaseMapper = null

  @Test
  def androidMessageListTraverse(): Unit = {
    val targetNumber = "904516937"

    androidDatabaseMapper.selectList(null).forEach(println)
  }
}
