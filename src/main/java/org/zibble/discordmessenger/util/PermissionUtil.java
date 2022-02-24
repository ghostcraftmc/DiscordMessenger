package org.zibble.discordmessenger.util;

import org.zibble.discordmessenger.components.Permission;
import org.zibble.discordmessenger.components.User;

public class PermissionUtil {

    public static boolean checkPermission(User member, Permission... permissions) {
        long effectivePerms = member.getEffectivePermission();
        return isApplied(effectivePerms, Permission.ADMINISTRATOR.getRawValue())
                || isApplied(effectivePerms, Permission.getRaw(permissions));
    }

    private static boolean isApplied(long permissions, long perms) {
        return (permissions & perms) == perms;
    }

}
