package xco.handler

import org.apache.ibatis.`type`.{BaseTypeHandler, JdbcType}
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import xco.util.DecryptUtil

import java.sql.{CallableStatement, PreparedStatement, ResultSet}

@Component
class AndroidDecryptHandler(decryptUtil: DecryptUtil) extends BaseTypeHandler[String] {
  extension (array: Array[Byte])
    def asUTF8String: String = String(array, "UTF-8")

  override def setNonNullParameter(ps: PreparedStatement, i: Int, t: String, jdbcType: JdbcType): Unit = {
    LoggerFactory.getLogger(classOf[AndroidDecryptHandler]).warn("只实现了解密功能")
    ps.setString(i, t)
  }

  override def getNullableResult(rs: ResultSet, columnName: String): String = decryptUtil.decrypt(rs.getBytes(columnName)).asUTF8String

  override def getNullableResult(rs: ResultSet, columnIndex: Int): String = decryptUtil.decrypt(rs.getBytes(columnIndex)).asUTF8String

  override def getNullableResult(cs: CallableStatement, columnIndex: Int): String = decryptUtil.decrypt(cs.getBytes(columnIndex)).asUTF8String
}
