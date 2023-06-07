package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.SelectMenu

data class SelectMenuReply(
    val button: SelectMenu,
    val discordMessage: DiscordMessage,
    val ephemeral: Boolean
) : JsonSerializable