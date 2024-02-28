package xco.message

import xco.util.DatatypeConverter
import xco.util.TLVUtil.TLV

class Image(source: List[TLV]) extends MessageContent {
  val md5: String = source.find(_.tag == 1).map(_.value).map(DatatypeConverter.printHexBinary).get
  val path: String = source.find(_.tag == 2).map(_.value).map(String(_, "UTF-16LE")).get
}