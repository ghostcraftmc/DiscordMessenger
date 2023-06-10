package org.zibble.discordmessenger.components.action.sendable

import org.zibble.discordmessenger.components.action.SendableAction
import org.zibble.discordmessenger.components.readable.DiscordMessage

data class ReplyMessageAction(
    val channelId: Long,
    val messageId: Long,
    val message: DiscordMessage
) : SendableAction("replyMessage", "Reply Message") {

    companion object {
        fun of(channelId: Long, messageId: Long, message: DiscordMessage) : ReplyMessageAction = ReplyMessageAction(
            channelId,
            messageId,
            message
        )
    }

}