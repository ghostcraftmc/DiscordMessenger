package org.zibble.discordmessenger.util

import org.zibble.discordmessenger.components.entity.Permission
import org.zibble.discordmessenger.components.entity.User

object PermissionUtil {

    fun checkPermission(member: User, vararg permissions: Permission): Boolean {
        val effectivePerms = member.effectivePermission
        return (isApplied(effectivePerms, Permission.ADMINISTRATOR.raw)
                || isApplied(effectivePerms, Permission.getRaw(*permissions)))
    }

    private fun isApplied(permissions: Long, perms: Long): Boolean {
        return permissions and perms == perms
    }

}