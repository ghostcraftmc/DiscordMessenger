package org.zibble.discordmessenger.components.action

import java.util.concurrent.ThreadLocalRandom

class AssignRoleAction(
    val memberId: Long,
    val roleId: Long
) : Action(ThreadLocalRandom.current().nextLong()) {

    companion object {
        fun of(memberId: Long, roleId: Long) : AssignRoleAction = AssignRoleAction(
            memberId = memberId,
            roleId = roleId
        )
    }

    override fun getKey(): String = "assignRole"

    override fun getName(): String = "Assign Role"

}