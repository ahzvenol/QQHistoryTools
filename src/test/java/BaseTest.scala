import com.google.protobuf.{ByteString, Descriptors, DynamicMessage, UnknownFieldSet}
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mybatis.spring.{SqlSessionTemplate, SqlSessionUtils}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import xco.Application
import xco.config.ExportConfig
import xco.util.FPUtil.|>
import xco.util.{AndroidDecryptUtil, MD5Util}

import java.io.{ByteArrayInputStream, DataInputStream}
import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{Files, Paths}
import java.nio.{Buffer, ByteBuffer, CharBuffer}
import java.sql.{Connection, ResultSet}
import java.util
import java.util.Date
import javax.xml.bind.DatatypeConverter
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}


@RunWith(classOf[SpringRunner])
@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[Application]))
class BaseTest {
  @Autowired
  val decryptUtil: AndroidDecryptUtil = null
  @Autowired
  val sqlSessionTemplate: SqlSessionTemplate = null

  lazy val connection: Connection = SqlSessionUtils.getSqlSession(
    sqlSessionTemplate.getSqlSessionFactory,
    sqlSessionTemplate.getExecutorType,
    sqlSessionTemplate.getPersistenceExceptionTranslator
  ).getConnection

  extension (resultSet: ResultSet)
    def getResultList[T](f: ResultSet => T): List[T] = {
      new Iterator[T] {
        def hasNext: Boolean = resultSet.next()

        def next: T = f(resultSet)
      }.toList
    }

  extension (array: Array[Byte])
    def asUTF8String: String = String(array, "UTF-8")

  @Test
  def traversalDatabase(): Unit = {
    val tableNameSet = connection.getMetaData.getTables(null, null, "%", null)

    val tableNameList = tableNameSet.getResultList(_.getString("TABLE_NAME"))

    for
      tableName <- tableNameList
      sql = s"select * from $tableName"
      resultSet <- Try(connection.createStatement().executeQuery(sql))
      dataList = resultSet.getResultList(row => 1.to(row.getMetaData.getColumnCount).map(i => row.getObject(i)))
    //      count = resultSet.getResultList(_.getRow).length
    do println(dataList)

    //    tableNameList
    //      .map(tableName => s"select * from $tableName")
    //      .map(sql => connection.createStatement().executeQuery(sql))
    //      .map(_.getResultList(row => 1.to(row.getMetaData.getColumnCount).map(i => row.getObject(i))))
    //      //          .map(_.getResultList(_.getRow).length)
    //      .foreach(println)
  }

  @Test
  def androidDataDecrypt(): Unit = {
    val sample = DatatypeConverter.parseHexBinary("D5BBB7D9AD98D7AABED9B0B3D99183D48CAAD7958CD8BD84D5AABED9A792D7A0B4D488BFDFA0BC")
    val result = decryptUtil.decrypt(sample.map(_.toByte).toArray).asUTF8String
    println(result.mkString)
  }

  @Test
  def androidProtoDataParse(): Unit = {
    import scala.collection.convert.ImplicitConversions.`map AsScala`
    val targetNumber = "904516937"
    val targetType: "troop" | "friend" = "troop"
    val resultSet = connection.createStatement()
      .executeQuery(s"select * from mr_${targetType}_${MD5Util.md5Encrypt32Upper(targetNumber)}_New where msgtype = -2000")
      .getResultList(row => (decryptUtil.decrypt(row.getBytes("msgData")), row.getString("shmsgseq")))
    for (rowMessage, source) <- resultSet.take(1) do
      //      println(rowMessage.asUTF8String)
      Try(UnknownFieldSet.parseFrom(rowMessage)) match
        case Success(value) =>
          println(value)
          println()
          println(value.asMap().toMap)
        case Failure(err) => println(err)
  }


  @Test
  def androidMessageListTraverse(): Unit = {
    val targetNumber = "904516937"
    val targetType: "troop" | "friend" = "troop"
    println(s"select * from mr_${targetType}_${MD5Util.md5Encrypt32Upper(targetNumber)}_New")
    val resultSet = connection.createStatement()
      .executeQuery(s"select * from mr_${targetType}_${MD5Util.md5Encrypt32Upper(targetNumber)}_New")
      .getResultList(row => (row.getBytes("msgData"), row.getString("shmsgseq")))

    for (rowMessage, source) <- resultSet do
      println((rowMessage.mkString("Array(", ", ", ")"), source))
      println(DatatypeConverter.printHexBinary(rowMessage))
      println(decryptUtil.decrypt(rowMessage).asUTF8String)
      println("---------------")
  }

  @Test
  def windowsDataDecode(): Unit = {
    val targetNumber = "904516937"
    val targetType: "group" | "buddy" = "group"
    val bytesArray = connection
      .createStatement()
      //      .executeQuery(s"select MsgContent from group_${targetNumber} where Rand = 2743318696")
      .executeQuery(s"select MsgContent from ${targetType}_${targetNumber}")
      .getResultList(_.getBytes(1))

    extension (is: DataInputStream)
      def readUnsignedInt(): Long = is.readInt() & 0x0FFFFFFFFL

      def readLittleEndianUnsignedShort(): Int = is.readUnsignedByte()
        | (is.readUnsignedByte() << 8)

      def readLittleEndianUnsignedInt(): Long = is.readUnsignedByte()
        | (is.readUnsignedByte() << 8)
        | (is.readUnsignedByte() << 16)
        | (is.readUnsignedByte() << 24)

    case class TLV(tag: Int, length: Int, value: Array[Byte])

    def readTLVGroup(is: DataInputStream): TLV = {
      val tag = is.readUnsignedByte()
      val length = is.readLittleEndianUnsignedShort()
      val value = is.readNBytes(length)
      TLV(tag, length, value)
    }

    for bytes <- bytesArray do
      val buf = DataInputStream(ByteArrayInputStream(bytes))
      //      println(DatatypeConverter.printHexBinary(bytes))
      buf.skip(8)
      val time = buf.readLittleEndianUnsignedInt()
      val rand = buf.readLittleEndianUnsignedInt()
      val color = buf.readLittleEndianUnsignedInt()
      val fontSize = buf.readUnsignedByte()
      val fontSylte = buf.readUnsignedByte()
      val charset = buf.readUnsignedByte()
      val fontFamily = buf.readUnsignedByte()
      val fontName = buf.readNBytes(buf.readLittleEndianUnsignedShort())
      buf.skip(2)
      println(Date(time * 1000))
      println(String(fontName, "UTF-16LE"))

      val listBuffer = ListBuffer[TLV]()
      val is = DataInputStream(ByteArrayInputStream(buf.readAllBytes()))
      while is.available() != 0 do listBuffer.addOne(readTLVGroup(is))
      val texts = List.from(listBuffer).filter(_.tag == 1).map(_.value)
        .map(e => readTLVGroup(DataInputStream(ByteArrayInputStream(e)))).map(_.value)
        .map(e => String(e, "UTF-16LE"))
      println(texts)
  }
}
