package xco.bot.event

import net.mamoe.mirai.contact.{Group, MemberKt, NormalMember}

trait OnGroup(val group: Group) {

	val groupId: Long = group.getId

	def getMember(memberId: Long): NormalMember = group.get(memberId)

	def getNameCardOrNick(memberId: Long): String = MemberKt.getNameCardOrNick(this.group.get(memberId))
}
