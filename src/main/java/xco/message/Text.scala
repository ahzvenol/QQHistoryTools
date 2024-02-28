package xco.message

import xco.util.TLVUtil.TLV

case class Text(source: List[TLV]) extends MessageContent {
  val tag: Int = 1
  val content: String = source.find(_.tag == 1).map(_.value).map(String(_, "UTF-16LE")).get
}