package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.JsonSerializable
import java.util.*

class Role(
    val id: Long,
    val name: String,
    val rawPermissions: Long,
    val color: Int,
    val position: Int
) : JsonSerializable {

    fun getPermissions(): EnumSet<Permission> {
        return Permission.getPermissions(rawPermissions)
    }

    fun hasPermission(vararg permissions: Permission): Boolean {
        val effectivePerms = rawPermissions or Permission.PUBLIC_PERMISSION_RAW
        for (perm in permissions) {
            if (effectivePerms and perm.raw != perm.raw) return false
        }
        return true
    }

}