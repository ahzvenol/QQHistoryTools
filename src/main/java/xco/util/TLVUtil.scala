package xco.util

import java.io.{ByteArrayInputStream, DataInputStream}
import scala.annotation.tailrec
import xco.util.DataInputStreamExtension.*

import scala.language.implicitConversions

object TLVUtil {
  case class TLV(tag: Int, length: Int, value: Array[Byte])

  def readTLV(is: DataInputStream): TLV = {
    val tag = is.readUnsignedByte()
    val length = is.readLittleEndianUnsignedShort()
    val value = is.readNBytes(length)
    TLV(tag, length, value)
  }

  def readTLVList(is: DataInputStream): List[TLV] =
    is.available() match
      case 0 => Nil
      case _ => readTLV(is) :: readTLVList(is)
    
  def readTLVList(array: Array[Byte]): List[TLV] = readTLVList(DataInputStream(ByteArrayInputStream(array)))
}
