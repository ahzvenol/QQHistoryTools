package xco.message

import xco.util.DatatypeConverter
import xco.util.TLVUtil.TLV

case class Image(source: List[TLV]) extends MessageContent {
  val hash: String = source.find(_.tag == 1).map(_.value).map(DatatypeConverter.printHexBinary).get
  val path: String = source.find(_.tag == 2).map(_.value).map(String(_, "UTF-16LE")).get
}