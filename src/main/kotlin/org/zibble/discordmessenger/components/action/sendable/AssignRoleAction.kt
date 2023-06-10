package org.zibble.discordmessenger.components.action.sendable

import org.zibble.discordmessenger.components.action.SendableAction

data class AssignRoleAction(
    val memberId: Long,
    val roleId: Long
) : SendableAction("assignRole", "Assign Role") {

    companion object {
        fun of(memberId: Long, roleId: Long) : AssignRoleAction = AssignRoleAction(memberId, roleId)
    }

}