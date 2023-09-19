package xco.handler

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler
import xco.handler.AndroidTableNameHandler.queryInfo
import xco.util.MD5Util

class AndroidTableNameHandler extends TableNameHandler {

  override def dynamicTableName(sql: String, tableName: String): String =
    tableName match
      case "mr_%s_%s_New[Android]" =>
        s"mr_${queryInfo.get.targetType}_${MD5Util.md5Encrypt32Upper(queryInfo.get.targetNumber)}_New"
      case _ => tableName

}

object AndroidTableNameHandler {
  case class AndroidMessageTableQueryInfo(targetType: "troop" | "friend", targetNumber: String)

  val queryInfo: ThreadLocal[AndroidMessageTableQueryInfo] = ThreadLocal()
}
