package xco.mapper

import org.springframework.stereotype.Component
import xco.handler.AndroidTableNameHandler
import xco.handler.AndroidTableNameHandler.AndroidTableQueryInfo

@Component
class AndroidDatabaseMapperWrapper(androidDatabaseMapper: AndroidDatabaseMapper) {
  def usingMapper[T](info: AndroidTableQueryInfo)(f: AndroidDatabaseMapper => T): T = {
    AndroidTableNameHandler.queryInfo.set(info)
    val res = f(androidDatabaseMapper)
    AndroidTableNameHandler.queryInfo.remove()
    res
  }
}
