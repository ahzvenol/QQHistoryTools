package xco.mapper

import org.springframework.stereotype.Component
import xco.handler.WindowsTableNameHandler
import xco.handler.WindowsTableNameHandler.WindowsMessageTableQueryInfo
import xco.mapper.WindowsMessageMapper

@Component
class WindowsMessageMapperWrapper(WindowsDatabaseMapper: WindowsMessageMapper) {
  def usingMapper[T](info: WindowsMessageTableQueryInfo)(f: WindowsMessageMapper => T): T = {
    WindowsTableNameHandler.queryInfo.set(info)
    val res = f(WindowsDatabaseMapper)
    WindowsTableNameHandler.queryInfo.remove()
    res
  }
}
