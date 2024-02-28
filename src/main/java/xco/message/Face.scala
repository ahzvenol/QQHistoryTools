package xco.message

import xco.util.TLVUtil.TLV

case class Face(source: List[TLV]) extends MessageContent {
  val id: Int = source.find(_.tag == 1).map(_.value).map(e => e(0).toInt | 0 << 8).get
}
