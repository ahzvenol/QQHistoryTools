package xco.handler

import org.apache.ibatis.`type`.{BaseTypeHandler, JdbcType, MappedJdbcTypes, MappedTypes}
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import xco.util.DecryptUtil

import java.sql.{CallableStatement, PreparedStatement, ResultSet}

@Component
@MappedTypes(Array(classOf[Array[Byte]]))
@MappedJdbcTypes(Array(JdbcType.BLOB,JdbcType.VARCHAR))
class AndroidDecryptHandler(decryptUtil: DecryptUtil) extends BaseTypeHandler[Array[Byte]] {
  override def setNonNullParameter(ps: PreparedStatement, i: Int, t: Array[Byte], jdbcType: JdbcType): Unit = {
    LoggerFactory.getLogger(classOf[AndroidDecryptHandler]).warn("只实现了解密功能")
    ps.setBytes(i, t)
  }

  override def getNullableResult(rs: ResultSet, columnName: String): Array[Byte] = decryptUtil.decrypt(rs.getBytes(columnName))

  override def getNullableResult(rs: ResultSet, columnIndex: Int): Array[Byte] = decryptUtil.decrypt(rs.getBytes(columnIndex))

  override def getNullableResult(cs: CallableStatement, columnIndex: Int): Array[Byte] = decryptUtil.decrypt(cs.getBytes(columnIndex))
}
