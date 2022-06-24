package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.Button

class ButtonReply(
    val button: Button,
    val discordMessage: DiscordMessage,
    val ephermal: Boolean
) : JsonSerializable {
}