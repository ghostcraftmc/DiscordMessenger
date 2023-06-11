package org.zibble.discordmessenger.components.action.sendable

import org.zibble.discordmessenger.components.action.SendableAction

data class RemoveRoleAction(
    val memberId: Long,
    val roleId: Long
) : SendableAction("removeRole", "Remove Role") {

    companion object {
        fun of(memberId: Long, roleId: Long) : RemoveRoleAction = RemoveRoleAction(memberId, roleId)
    }

}