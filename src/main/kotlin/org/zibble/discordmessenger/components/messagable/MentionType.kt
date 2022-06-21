package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.components.JsonSerializable

enum class MentionType : JsonSerializable {

    USER,
    ROLE,
    CHANNEL,
    EMOTE,
    HERE,
    EVERYONE,
    ;

}