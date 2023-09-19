package xco.handler

import org.apache.ibatis.`type`.{BaseTypeHandler, JdbcType, MappedJdbcTypes, MappedTypes}
import org.springframework.stereotype.Component

import java.sql.{CallableStatement, PreparedStatement, ResultSet}

@Component
@MappedTypes(Array(classOf[Array[Byte]]))
@MappedJdbcTypes(Array(JdbcType.BLOB))
class ByteBlobHandler extends BaseTypeHandler[Array[Byte]] {
  override def setNonNullParameter(ps: PreparedStatement, i: Int, t: Array[Byte], jdbcType: JdbcType): Unit = ps.setBytes(i, t)

  override def getNullableResult(rs: ResultSet, columnName: String): Array[Byte] = rs.getBytes(columnName)

  override def getNullableResult(rs: ResultSet, columnIndex: Int): Array[Byte] = rs.getBytes(columnIndex)

  override def getNullableResult(cs: CallableStatement, columnIndex: Int): Array[Byte] = cs.getBytes(columnIndex)
}
