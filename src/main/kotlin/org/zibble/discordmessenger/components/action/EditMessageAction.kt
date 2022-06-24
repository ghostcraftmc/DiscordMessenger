package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.readable.DiscordMessage
import java.util.concurrent.ThreadLocalRandom

class EditMessageAction(
    override val id: Long = ThreadLocalRandom.current().nextLong(),
    val channelId: Long,
    val messageId: Long,
    val newMessage: DiscordMessage
) : Action(id) {

    companion object {
        fun of(channelId: Long, messageId: Long, newMessage: DiscordMessage) : EditMessageAction = EditMessageAction(
            channelId = channelId,
            messageId = messageId,
            newMessage = newMessage)
    }

    override fun getKey(): String = "editMessage"

    override fun getName(): String = "Edit Message"

}