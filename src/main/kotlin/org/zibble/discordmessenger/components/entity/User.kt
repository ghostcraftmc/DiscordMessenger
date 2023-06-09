package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.util.checkPermission
import java.util.*

data class User(
    val id: Long,
    val discriminator: String,
    val name: String,
    val avatarId: String?,
    val bot: Boolean,
    val owner: Boolean,
    val roles: Collection<Role>,
    val effectivePermission: Long
) : JsonSerializable {

    fun getPermissions(): EnumSet<Permission> {
        return Permission.getPermissions(effectivePermission)
    }

    fun hasPermission(vararg permissions: Permission): Boolean {
        return this.checkPermission(*permissions)
    }

}