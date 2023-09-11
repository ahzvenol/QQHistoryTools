package xco.util

import com.alibaba.fastjson.JSON
import org.springframework.stereotype.Component
import xco.config.ExportConfig
import xco.util.FPUtil.|>

import java.io.*
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.Base64
import scala.collection.mutable.ListBuffer
import scala.util.Using

@Component
class DecryptUtil(config: ExportConfig) {

  val UnserializedDataType: Array[Int] = Array(-1000, -1051, -1012, -2042, -2015, -1034, -2005, -3008, -2016, -4008, -1013, -2009, -1001, -5018, -5012)
  val ProtoDataType: Array[Int] = Array(-2000, -1035, -2002, -2022, -5020, -5023, -8018, -5040)
  val JavaSerializedDataType: Array[Int] = Array(-1049, -2017, -2025, -2011, -5008, -2007, -2025)

  lazy val crc64Table: IndexedSeq[Long] = {
    for i <- 0 until 256 yield
      Range(0, 8).foldLeft[Long](i) {
        case (bf, _) if (bf & 1) != 0 => bf >> 1 ^ -7661587058870466123L
        case (bf, _) => bf >> 1
      }
  }

  def decrypt(data: Array[Byte] | String): String = {
    val key = config.key
    val bytes = data match
      case arr: Array[Byte] => arr
      case s: String => s.getBytes
    //    Range(0, bytes.length).foldLeft("")((m, i) => m + (bytes(i) ^ key(i % key.length)).toChar)
    val resultByteSeq = bytes.indices.map(i => (bytes(i) ^ key(i % key.length)).toByte)
    String(resultByteSeq.toArray,"UTF-8")
  }

  def base64Decode(data: String): String = data.getBytes("UTF-8")
    //    |> Base64.getDecoder.decode
    |> Base64.getDecoder.decode
    |> (e => String(e, "UTF-8"))

  def unSerialization(hexSer: String): Unit = {
    def hexStringToByteArray(hexString: String): Array[Byte] = {
      for i <- Range(0, hexString.length / 2).toArray yield
        ((Character.digit(hexString.charAt(i * 2), 16) << 4) + Character.digit(hexString.charAt(i * 2 + 1), 16)).toByte
    }

    val serialization = hexStringToByteArray(hexSer)
    val data = Using.resource(ObjectInputStream(ByteArrayInputStream(serialization)))(_.readObject())
    print(JSON.toJSONString(data))
  }

}
