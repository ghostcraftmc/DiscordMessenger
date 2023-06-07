package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable

data class CommandReply(
    val commandId: Long,
    val message: DiscordMessage,
    val ephemeral: Boolean = false
) : JsonSerializable