package xco.mapper

import org.springframework.stereotype.Component
import xco.handler.AndroidTableNameHandler
import xco.handler.AndroidTableNameHandler.AndroidMessageTableQueryInfo
import xco.mapper.AndroidMessageMapper

@Component
class AndroidMessageMapperWrapper(androidDatabaseMapper: AndroidMessageMapper) {
  def usingMapper[T](info: AndroidMessageTableQueryInfo)(f: AndroidMessageMapper => T): T = {
    AndroidTableNameHandler.queryInfo.set(info)
    val res = f(androidDatabaseMapper)
    AndroidTableNameHandler.queryInfo.remove()
    res
  }
}
