package org.zibble.discordmessenger.components.action.sendable

import org.zibble.discordmessenger.components.action.SendableAction

data class DeleteMessageAction(
    val channelId: Long,
    val messageId: Long
) : SendableAction("deleteMessage", "Delete Message") {

    companion object {
        fun of(channelId: Long, messageId: Long) : DeleteMessageAction = DeleteMessageAction(channelId, messageId)
    }

}