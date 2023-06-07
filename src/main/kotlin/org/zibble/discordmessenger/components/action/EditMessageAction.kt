package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.readable.DiscordMessage
import java.util.concurrent.ThreadLocalRandom

data class EditMessageAction(
    val channelId: Long,
    val messageId: Long,
    val newMessage: DiscordMessage
) : Action(ThreadLocalRandom.current().nextLong()) {

    companion object {
        fun of(channelId: Long, messageId: Long, newMessage: DiscordMessage) : EditMessageAction = EditMessageAction(
            channelId,
            messageId,
            newMessage
        )
    }

    override fun getKey(): String = "editMessage"

    override fun getName(): String = "Edit Message"

}