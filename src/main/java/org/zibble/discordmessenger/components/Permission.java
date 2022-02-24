package org.zibble.discordmessenger.components;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;

public enum Permission {

    // General Server / Channel Permissions
    MANAGE_CHANNEL(             4, true,  true,  "Manage Channels"),
    MANAGE_SERVER(              5, true,  false, "Manage Server"),
    VIEW_AUDIT_LOGS(            7, true,  false, "View Audit Logs"),
    VIEW_CHANNEL(              10, true,  true,  "View Channel(s)"),
    VIEW_GUILD_INSIGHTS(       19, true,  false, "View Server Insights"),
    MANAGE_ROLES(              28, true,  false, "Manage Roles"),
    MANAGE_PERMISSIONS(        28, false, true,  "Manage Permissions"),
    MANAGE_WEBHOOKS(           29, true,  true,  "Manage Webhooks"),
    MANAGE_EMOTES_AND_STICKERS(30, true,  false, "Manage Emojis and Stickers"),

    // Membership Permissions
    CREATE_INSTANT_INVITE(0, true, true,  "Create Instant Invite"),
    KICK_MEMBERS(         1, true, false, "Kick Members"),
    BAN_MEMBERS(          2, true, false, "Ban Members"),
    NICKNAME_CHANGE(     26, true, false, "Change Nickname"),
    NICKNAME_MANAGE(     27, true, false, "Manage Nicknames"),
    MODERATE_MEMBERS(    40, true, false, "Timeout Members"),

    // Text Permissions
    MESSAGE_ADD_REACTION(     6, true, true, "Add Reactions"),
    MESSAGE_SEND(            11, true, true, "Send Messages"),
    MESSAGE_TTS(             12, true, true, "Send TTS Messages"),
    MESSAGE_MANAGE(          13, true, true, "Manage Messages"),
    MESSAGE_EMBED_LINKS(     14, true, true, "Embed Links"),
    MESSAGE_ATTACH_FILES(    15, true, true, "Attach Files"),
    MESSAGE_HISTORY(         16, true, true, "Read History"),
    MESSAGE_MENTION_EVERYONE(17, true, true, "Mention Everyone"),
    MESSAGE_EXT_EMOJI(       18, true, true, "Use External Emojis"),
    USE_APPLICATION_COMMANDS(31, true, true, "Use Application Commands"),
    MESSAGE_EXT_STICKER(     37, true, true, "Use External Stickers"),

    // Thread Permissions
    MANAGE_THREADS(          34, true, true, "Manage Threads"),
    CREATE_PUBLIC_THREADS(   35, true, true, "Create Public Threads"),
    CREATE_PRIVATE_THREADS(  36, true, true, "Create Private Threads"),
    MESSAGE_SEND_IN_THREADS( 38, true, true, "Send Messages in Threads"),

    // Voice Permissions
    PRIORITY_SPEAKER(       8, true, true, "Priority Speaker"),
    VOICE_STREAM(           9, true, true, "Video"),
    VOICE_CONNECT(         20, true, true, "Connect"),
    VOICE_SPEAK(           21, true, true, "Speak"),
    VOICE_MUTE_OTHERS(     22, true, true, "Mute Members"),
    VOICE_DEAF_OTHERS(     23, true, true, "Deafen Members"),
    VOICE_MOVE_OTHERS(     24, true, true, "Move Members"),
    VOICE_USE_VAD(         25, true, true, "Use Voice Activity"),
    VOICE_START_ACTIVITIES(39, true, true, "Launch Activities in Voice Channels"),

    // Stage Channel Permissions
    REQUEST_TO_SPEAK(      32, true, true, "Request to Speak"),

    // Advanced Permissions
    ADMINISTRATOR(3, true, false, "Administrator"),


    UNKNOWN(-1, false, false, "Unknown");

    /**
     * Empty array of Permission enum, useful for optimized use in {@link java.util.Collection#toArray(Object[])}.
     */
    // This is an optimization suggested by Effective Java 3rd Edition - Item 54
    public static final Permission[] EMPTY_PERMISSIONS = new Permission[0];

    /**
     * Represents a raw set of all permissions
     */
    public static final long ALL_PERMISSIONS = Permission.getRaw(Permission.values());

    /**
     * All permissions that apply to a channel
     */
    public static final long ALL_CHANNEL_PERMISSIONS = Permission.getRaw(Arrays.stream(values())
            .filter(Permission::isChannel).collect(Collectors.toSet()));

    /**
     * All Guild specific permissions which are only available to roles
     */
    public static final long ALL_GUILD_PERMISSIONS = Permission.getRaw(Arrays.stream(values())
            .filter(Permission::isGuild).collect(Collectors.toSet()));

    /**
     * All text channel specific permissions which are only available in text channel permission overrides
     */
    public static final long ALL_TEXT_PERMISSIONS
            = Permission.getRaw(MESSAGE_ADD_REACTION, MESSAGE_SEND, MESSAGE_TTS, MESSAGE_MANAGE,
            MESSAGE_EMBED_LINKS, MESSAGE_ATTACH_FILES, MESSAGE_EXT_EMOJI, MESSAGE_EXT_STICKER,
            MESSAGE_HISTORY, MESSAGE_MENTION_EVERYONE, USE_APPLICATION_COMMANDS,
            MANAGE_THREADS, CREATE_PUBLIC_THREADS, CREATE_PRIVATE_THREADS, MESSAGE_SEND_IN_THREADS);

    /**
     * All voice channel specific permissions which are only available in voice channel permission overrides
     */
    public static final long ALL_VOICE_PERMISSIONS
            = Permission.getRaw(VOICE_STREAM, VOICE_CONNECT, VOICE_SPEAK, VOICE_MUTE_OTHERS,
            VOICE_DEAF_OTHERS, VOICE_MOVE_OTHERS, VOICE_USE_VAD,
            PRIORITY_SPEAKER, REQUEST_TO_SPEAK, VOICE_START_ACTIVITIES);

    public static long PUBLIC_PERMISSION_RAW = 0;

    private final int offset;
    private final long raw;
    private final boolean isGuild, isChannel;
    private final String name;

    Permission(int offset, boolean isGuild, boolean isChannel, @NotNull String name) {
        this.offset = offset;
        this.raw = 1L << offset;
        this.isGuild = isGuild;
        this.isChannel = isChannel;
        this.name = name;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public int getOffset() {
        return offset;
    }

    public long getRawValue() {
        return raw;
    }

    public boolean isGuild() {
        return isGuild;
    }

    public boolean isChannel() {
        return isChannel;
    }

    public boolean isText() {
        return (raw & ALL_TEXT_PERMISSIONS) == raw;
    }

    public boolean isVoice() {
        return (raw & ALL_VOICE_PERMISSIONS) == raw;
    }

    public static Permission getFromOffset(int offset) {
        for (Permission perm : values()) {
            if (perm.offset == offset)
                return perm;
        }
        return UNKNOWN;
    }

    @NotNull
    public static EnumSet<Permission> getPermissions(long permissions) {
        if (permissions == 0)
            return EnumSet.noneOf(Permission.class);
        EnumSet<Permission> perms = EnumSet.noneOf(Permission.class);
        for (Permission perm : Permission.values())
        {
            if (perm != UNKNOWN && (permissions & perm.raw) == perm.raw)
                perms.add(perm);
        }
        return perms;
    }

    public static long getRaw(@NotNull Permission... permissions) {
        long raw = 0;
        for (Permission perm : permissions)
        {
            if (perm != null && perm != UNKNOWN)
                raw |= perm.raw;
        }

        return raw;
    }

    public static long getRaw(@NotNull Collection<Permission> permissions) {
        return getRaw(permissions.toArray(EMPTY_PERMISSIONS));
    }

}
