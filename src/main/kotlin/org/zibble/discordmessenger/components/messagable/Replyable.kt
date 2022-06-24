package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.components.readable.DiscordMessage

interface Replyable {

    fun reply(message: DiscordMessage, ephermal: Boolean)

}