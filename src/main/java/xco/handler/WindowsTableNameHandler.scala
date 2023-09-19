package xco.handler

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler
import xco.handler.WindowsTableNameHandler.queryInfo
import xco.util.MD5Util

class WindowsTableNameHandler extends TableNameHandler {

  override def dynamicTableName(sql: String, tableName: String): String =
    println(tableName)
    tableName match
      case "%s_%s[Windows]" => 
        s"${queryInfo.get.targetType}_${queryInfo.get.targetNumber}"
      case _ => tableName

}

object WindowsTableNameHandler {
  case class WindowsMessageTableQueryInfo(targetType: "group" | "buddy", targetNumber: String)

  val queryInfo: ThreadLocal[WindowsMessageTableQueryInfo] = ThreadLocal()
}
