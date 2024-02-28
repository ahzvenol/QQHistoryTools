package xco.handler

import org.apache.ibatis.`type`.{BaseTypeHandler, ByteArrayTypeHandler, JdbcType, MappedJdbcTypes, MappedTypes}
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import xco.util.AndroidDecryptUtil

import java.sql.{CallableStatement, PreparedStatement, ResultSet}

@Component
@MappedTypes(Array(classOf[Array[Byte]]))
@MappedJdbcTypes(Array(JdbcType.BLOB, JdbcType.VARCHAR))
class AndroidDecryptHandler(decryptUtil: AndroidDecryptUtil) extends ByteArrayTypeHandler {
  override def setNonNullParameter(ps: PreparedStatement, i: Int, t: Array[Byte], jdbcType: JdbcType): Unit = {
    LoggerFactory.getLogger(classOf[AndroidDecryptHandler]).warn("只实现了解密功能")
    super.setNonNullParameter(ps, i, t, jdbcType)
  }

  override def getNullableResult(rs: ResultSet, columnName: String): Array[Byte] = decryptUtil.decrypt(super.getNullableResult(rs, columnName))

  override def getNullableResult(rs: ResultSet, columnIndex: Int): Array[Byte] = decryptUtil.decrypt(super.getNullableResult(rs, columnIndex))

  override def getNullableResult(cs: CallableStatement, columnIndex: Int): Array[Byte] = decryptUtil.decrypt(super.getNullableResult(cs, columnIndex))
}
