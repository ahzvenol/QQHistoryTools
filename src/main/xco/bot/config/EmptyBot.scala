package xco.bot.config

import kotlin.coroutines.{Continuation, CoroutineContext}
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.friendgroup.FriendGroups
import net.mamoe.mirai.contact.{AvatarSpec, ContactList, Friend, Group, OtherClient, Stranger}
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.message.action.BotNudge
import net.mamoe.mirai.utils.{BotConfiguration, MiraiLogger}

class EmptyBot() extends Bot {
  override def getAsFriend: Friend = null

  override def getAsStranger: Stranger = null

  override def getConfiguration: BotConfiguration = null

  override def getEventChannel: EventChannel[BotEvent] = null

  override def getFriendGroups: FriendGroups = null

  override def getFriends: ContactList[Friend] = null

  override def getGroups: ContactList[Group] = null

  override def isOnline: Boolean = false

  override def getLogger: MiraiLogger = null

  override def getOtherClients: ContactList[OtherClient] = null

  override def getStrangers: ContactList[Stranger] = null

  override def close(throwable: Throwable): Unit = {}

  override def login(continuation: Continuation[_ >: kotlin.Unit]): AnyRef = null

  override def getNick: String = null

  override def getId: Long = -1

  override def getCoroutineContext: CoroutineContext = null

  override def login(): Unit = {}

  override def join(): Unit = {}

  override def closeAndJoin(cause: Throwable): Unit = {}

  override def getBot: Bot = null

  override def close(): Unit = {}

  override def closeAndJoin(cause: Throwable, $completion: Continuation[_ >: kotlin.Unit]): AnyRef = null

  override def getFriend(id: Long): Friend = null

  override def getFriendOrFail(id: Long): Friend = null

  override def getGroup(id: Long): Group = null

  override def getGroupOrFail(id: Long): Group = null

  override def getStranger(id: Long): Stranger = null

  override def getStrangerOrFail(id: Long): Stranger = null

  override def join($completion: Continuation[_ >: kotlin.Unit]): AnyRef = null

  override def nudge(): BotNudge = null

  override def getAvatarUrl: String = null

  override def getAvatarUrl(spec: AvatarSpec): String = null
}
