package org.zibble.discordmessenger.components.action

class RemoveRoleAction(
    val memberId: Long,
    val roleId: Long
) : Action() {

    companion object {
        fun of(memberId: Long, roleId: Long) : RemoveRoleAction = RemoveRoleAction(memberId, roleId)
    }

    override fun getKey(): String = "removeRole"

    override fun getName(): String = "Remove Role"

}