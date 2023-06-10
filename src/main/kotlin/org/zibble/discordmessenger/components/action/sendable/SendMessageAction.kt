package org.zibble.discordmessenger.components.action.sendable

import org.zibble.discordmessenger.components.action.SendableAction
import org.zibble.discordmessenger.components.readable.DiscordMessage

data class SendMessageAction(
    val channelId: Long,
    val message: DiscordMessage
) : SendableAction("sendMessage", "Send Message") {

    companion object {
        fun of(channelId: Long, message: DiscordMessage) : SendMessageAction = SendMessageAction(
            channelId,
            message
        )
    }

}