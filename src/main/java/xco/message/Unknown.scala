package xco.message

import xco.util.TLVUtil.TLV

case class Unknown(source: TLV) extends MessageContent {
  val tag: Int = source.tag
  val content: Array[Byte] = source.value
}
