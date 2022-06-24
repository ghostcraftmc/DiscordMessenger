package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.SelectMenu

class SelectMenuReply(
    val button: SelectMenu,
    val discordMessage: DiscordMessage,
    val ephermal: Boolean
) : JsonSerializable {
}