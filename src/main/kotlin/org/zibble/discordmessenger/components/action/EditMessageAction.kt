package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.readable.DiscordMessage

class EditMessageAction(
    val channelId: Long,
    val messageId: Long,
    val newMessage: DiscordMessage
) : Action() {

    companion object {
        fun of(channelId: Long, messageId: Long, newMessage: DiscordMessage) : EditMessageAction = EditMessageAction(channelId, messageId, newMessage)
    }

    override fun getKey(): String = "editMessage"

    override fun getName(): String = "Edit Message"

}