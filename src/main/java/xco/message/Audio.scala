package xco.message

import xco.util.DatatypeConverter
import xco.util.TLVUtil.TLV

case class Audio(source: List[TLV])  extends MessageContent{
  val hash: String = source.find(_.tag == 1).map(_.value).map(DatatypeConverter.printHexBinary).get
}

