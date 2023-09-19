package xco.util

import java.io.DataInputStream

object DataInputStreamDecorator {
  extension (is: DataInputStream)
    def readUnsignedInt(): Long = is.readInt() & 0x0FFFFFFFFL

    def readLittleEndianUnsignedShort(): Int = is.readUnsignedByte()
      | (is.readUnsignedByte() << 8)

    def readLittleEndianUnsignedInt(): Long = is.readUnsignedByte()
      | (is.readUnsignedByte() << 8)
      | (is.readUnsignedByte() << 16)
      | (is.readUnsignedByte() << 24)
}
