package xco.message

import java.io.{ByteArrayInputStream, DataInputStream}
import xco.util.DataInputStreamDecorator.*

class AbstractWindowsMessage(is: DataInputStream) {
  is.skip(8)
  val time: Long = is.readLittleEndianUnsignedInt()
  val rand: Long = is.readLittleEndianUnsignedInt()
  val color: Long = is.readLittleEndianUnsignedInt()
  val fontSize: Int = is.readUnsignedByte()
  val fontStyle: Int = is.readUnsignedByte()
  val charset: Int = is.readUnsignedByte()
  val fontFamily: Int = is.readUnsignedByte()
  val fontName: String = String(is.readNBytes(is.readLittleEndianUnsignedShort()), "UTF-16LE")
  is.skip(2)
}

object AbstractWindowsMessage {
  def apply(bytes: Array[Byte]) = new AbstractWindowsMessage(DataInputStream(ByteArrayInputStream(bytes)))
}
