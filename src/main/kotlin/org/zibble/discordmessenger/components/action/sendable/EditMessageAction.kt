package org.zibble.discordmessenger.components.action.sendable

import org.zibble.discordmessenger.components.action.SendableAction
import org.zibble.discordmessenger.components.readable.DiscordMessage

data class EditMessageAction(
    val channelId: Long,
    val messageId: Long,
    val newMessage: DiscordMessage
) : SendableAction("editMessage", "Edit Message") {

    companion object {
        fun of(channelId: Long, messageId: Long, newMessage: DiscordMessage) : EditMessageAction = EditMessageAction(
            channelId,
            messageId,
            newMessage
        )
    }

}