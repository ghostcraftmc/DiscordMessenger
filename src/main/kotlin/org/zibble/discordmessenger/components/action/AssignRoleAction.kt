package org.zibble.discordmessenger.components.action

class AssignRoleAction(
    val memberId: Long,
    val roleId: Long
) : Action() {

    companion object {
        fun of(memberId: Long, roleId: Long) : AssignRoleAction = AssignRoleAction(memberId, roleId)
    }

    override fun getKey(): String = "assignRole"

    override fun getName(): String = "Assign Role"

}