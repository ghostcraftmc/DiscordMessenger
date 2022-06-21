package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.JsonSerializable

class Emote private constructor(
    val value: String,
    val guildId: Long,
    val unicode: Boolean
) : JsonSerializable {

    companion object {
        fun unicode(code: String) : Emote = Emote(code, 0L, true)

        fun emoji(guildId: Long, value: String) : Emote = Emote(value, guildId, false)
    }

}