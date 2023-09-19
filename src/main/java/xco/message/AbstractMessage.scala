package xco.message

import java.util.Date

trait AbstractMessage {
    val senderId: Long
    val senderName: String
    val timeStamp: Date
    val source: Int
    val quote: Int | Null
    lazy val messageList: List[Map[String, Any]]
}
