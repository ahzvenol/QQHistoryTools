package xco.message

import xco.entity.WindowsMessageTableDO

import java.io.ByteArrayInputStream
import xco.util.DataInputStream
import xco.util.TLVUtil
import xco.util.TLVUtil.{TLV, readTLVList}

import java.util.Date

class WindowsMessage(entity: WindowsMessageTableDO) {
  val id: Int = entity.getSeq
  val senderId: Int = entity.getSenderUin
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
  //  val messageChain: List[(Int, List[TLV])] = TLVUtil
  //    .readTLVList(is.readAllBytes())
  //    .map(tlv => (tlv.tag, TLVUtil.readTLVList(tlv.value)))
  val messageChain: List[MessageContent] = TLVUtil
    .readTLVList(is.readAllBytes())
    .map(parseMessage)

  def parseMessage(message: TLV): MessageContent = {
    val content = TLVUtil.readTLVList(message.value)
    message.tag match
      case 1 => Text(content)
      case 2 => Face(content)
      case 3 | 6 => Image(content)
      case 7 => Audio(content)
      //      case 18 => ()
      case 26 => Video(content)
      case _ => Unknown(message)
  }
}
