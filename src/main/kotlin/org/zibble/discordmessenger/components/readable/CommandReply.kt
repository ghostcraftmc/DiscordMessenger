package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable

class CommandReply(
    val commandId: Long,
    val message: DiscordMessage,
    val ephermal: Boolean = false
) : JsonSerializable