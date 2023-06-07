package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.Button

data class ButtonReply(
    val button: Button,
    val discordMessage: DiscordMessage,
    val ephemeral: Boolean
) : JsonSerializable