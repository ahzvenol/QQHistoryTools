package xco.message

import xco.entity.WindowsMessageTableDO

import java.io.{ByteArrayInputStream, DataInputStream}
import xco.util.DataInputStreamExtension.*
import xco.util.TLVUtil
import xco.util.TLVUtil.TLV

import java.util.Date

class AbstractWindowsMessage(entity: WindowsMessageTableDO) {
  val senderId: Int = entity.getSenderId
  val timeStamp: Date = entity.getTime
  private val is = DataInputStream(ByteArrayInputStream(entity.getMsgContent))
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
  val messageChain: List[TLV] = TLVUtil.readTLVList(is.readAllBytes())
}
