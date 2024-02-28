package xco.message

import xco.util.DatatypeConverter
import xco.util.TLVUtil.TLV

case class Video(source: List[TLV]) extends MessageContent {
  val hash: String = source.find(_.tag == 1)
    .map(_.value.slice(244, 244 + 16))
    .map(_.map(_ ^ 0xEF).map(_.toByte))
    .map(DatatypeConverter.printHexBinary).get
}
