package org.zibble.discordmessenger.util

import org.zibble.discordmessenger.components.entity.Permission
import org.zibble.discordmessenger.components.entity.User

// ----------------------------------- //
// EncodingUtil
// ----------------------------------- //
fun String.decodeCodepoint(radix: Int = 16): String {
    require(this.startsWith("U+")) { "Invalid format" }
    val codePoint = Integer.parseUnsignedInt(this.substring(2), radix)
    return String(Character.toChars(codePoint))
}

// ----------------------------------- //
// PermissionUtil
// ----------------------------------- //
fun User.checkPermission(vararg permissions: Permission): Boolean {
    val effectivePerms = this.effectivePermission
    return (isApplied(effectivePerms, Permission.ADMINISTRATOR.raw)
            || isApplied(effectivePerms, Permission.getRaw(*permissions)))
}

private fun isApplied(permissions: Long, perms: Long): Boolean {
    return permissions and perms == perms
}