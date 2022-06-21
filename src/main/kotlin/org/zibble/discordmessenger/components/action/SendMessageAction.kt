package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.readable.DiscordMessage

class SendMessageAction(
    val channelId: Long,
    val message: DiscordMessage
) : Action() {

    companion object {
        fun of(channelId: Long, message: DiscordMessage) : SendMessageAction = SendMessageAction(channelId, message)
    }

    override fun getKey(): String = "sendMessage"

    override fun getName(): String = "Send Message"

}