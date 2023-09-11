import com.google.protobuf.{Descriptors, DynamicMessage, UnknownFieldSet}
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
import xco.util.{DecryptUtil, MD5Util}

import java.io.{ByteArrayInputStream, DataInputStream}
import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{Files, Paths}
import java.nio.{Buffer, ByteBuffer, CharBuffer}
import java.sql.{Connection, ResultSet}
import java.util
import javax.xml.bind.DatatypeConverter
import scala.util.{Failure, Success, Try}


@RunWith(classOf[SpringRunner])
@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[Application]))
class MapperTest() {
  @Autowired
  val decryptUtil: DecryptUtil = null
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
    val sample = List(-41, -109, -108, -43, -98, -92, 112, -44, -126, -80, -43, -75, -102, -41, -78, -100, -40, -87, -103)
    val result = decryptUtil.decrypt(sample.map(_.toByte).toArray)
    println(result.mkString("Array(", ", ", ")"))
    assert(result == "确实@一只爬虫")
  }

  @Test
  def androidProtoDataParse(): Unit = {
    val targetNumber = "904516937"
    val targetType: "troop" | "friend" = "troop"
    val resultSet = connection.createStatement()
      .executeQuery(s"select * from mr_${targetType}_${MD5Util.md5Encrypt32Upper(targetNumber)}_New where msgtype = -1035")
      .getResultList(row => (row.getBytes("msgData"), row.getString("shmsgseq")))
    for (rowMessage, source) <- resultSet.take(1) do
      Try(UnknownFieldSet.parseFrom(rowMessage)) match
        case Success(value) => println(value)
        case Failure(_) =>
          //  Files.write(Paths.get(s"sample-$source.hex"), rowMessage)
          println(String(rowMessage,"utf-8"))
  }


  @Test
  def androidMessageListTraverse(): Unit = {
    val targetNumber = "904516937"
    val targetType: "troop" | "friend" = "troop"

    val resultSet = connection.createStatement()
      .executeQuery(s"select * from mr_${targetType}_${MD5Util.md5Encrypt32Upper(targetNumber)}_New")
      .getResultList(row => (row.getBytes("msgData"), row.getString("shmsgseq")))

    for (rowMessage, source) <- resultSet do
      println((rowMessage.mkString("Array(", ", ", ")"), source))
      println(rowMessage |> DatatypeConverter.printHexBinary)
      println(rowMessage |> decryptUtil.decrypt)
      println("---------------")
  }

  @Test
  def windowsDataDecode(): Unit = {
    val targetNumber = "904516937"
    val bytesArray = connection
      .createStatement()
      //      .executeQuery(s"select MsgContent from group_${targetNumber} where Rand = 2743318696")
      .executeQuery(s"select MsgContent from group_$targetNumber")
      .getResultList(_.getBytes(1))

    extension (is: DataInputStream)
      def readUnsignedInt(): Long = is.readInt() & 0x0FFFFFFFFL

      def readLittleEndianUnsignedShort(): Int = is.readUnsignedByte()
        | (is.readUnsignedByte() << 8)

      def readLittleEndianUnsignedInt(): Long = is.readUnsignedByte()
        | (is.readUnsignedByte() << 8)
        | (is.readUnsignedByte() << 16)
        | (is.readUnsignedByte() << 24)

    case class TLVGroup(tag: Int, length: Int, value: Array[Byte])

    def readTLVGroup(is: DataInputStream): TLVGroup = {
      val tag = is.readUnsignedByte()
      val length = is.readUnsignedShort()
      val value = is.readNBytes(length)
      TLVGroup(tag, length, value)
    }

    for bytes <- bytesArray do
      val buf = DataInputStream(ByteArrayInputStream(bytes))
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
      println(time)
      println(String(fontName, "UTF-16LE"))

      while buf.available() != 0 do
        println(readTLVGroup(buf))
  }
}
