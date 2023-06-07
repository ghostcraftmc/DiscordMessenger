package org.zibble.discordmessenger.components.action

import java.util.concurrent.ThreadLocalRandom

data class RemoveRoleAction(
    val memberId: Long,
    val roleId: Long
) : Action(ThreadLocalRandom.current().nextLong()) {

    companion object {
        fun of(memberId: Long, roleId: Long) : RemoveRoleAction = RemoveRoleAction(memberId, roleId)
    }

    override fun getKey(): String = "removeRole"

    override fun getName(): String = "Remove Role"

}