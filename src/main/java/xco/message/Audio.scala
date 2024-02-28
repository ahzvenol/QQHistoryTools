package xco.message

import xco.util.DatatypeConverter
import xco.util.TLVUtil.TLV

class Audio(source: List[TLV])  extends MessageContent{
  val md5: String = source.find(_.tag == 1).map(_.value).map(DatatypeConverter.printHexBinary).get
}

