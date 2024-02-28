package xco.util

import java.io.{DataInputStream, InputStream}

class DataInputStream(in: InputStream) extends java.io.DataInputStream(in) {
  def readUnsignedInt(): Long = this.readInt() & 0x0FFFFFFFFL

  def readLittleEndianUnsignedShort(): Int = this.readUnsignedByte()
    | (this.readUnsignedByte() << 8)

  def readLittleEndianUnsignedInt(): Long = this.readUnsignedByte()
    | (this.readUnsignedByte() << 8)
    | (this.readUnsignedByte() << 16)
    | (this.readUnsignedByte() << 24)
}
