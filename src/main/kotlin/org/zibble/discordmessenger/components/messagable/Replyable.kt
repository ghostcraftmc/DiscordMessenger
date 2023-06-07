package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.components.readable.DiscordMessage

interface Replyable {

    suspend fun reply(message: DiscordMessage, ephemeral: Boolean)

}