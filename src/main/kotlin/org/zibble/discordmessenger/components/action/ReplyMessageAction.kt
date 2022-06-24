package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.readable.DiscordMessage
import java.util.concurrent.ThreadLocalRandom

class ReplyMessageAction(
    override val id: Long = ThreadLocalRandom.current().nextLong(),
    val channelId: Long,
    val messageId: Long,
    val message: DiscordMessage
) : Action(id) {

    companion object {
        fun of(channelId: Long, messageId: Long, message: DiscordMessage) : ReplyMessageAction = ReplyMessageAction(
            channelId = channelId,
            messageId = messageId,
            message = message
        )
    }

    override fun getKey(): String = "replyMessage"

    override fun getName(): String = "Reply Message"
}