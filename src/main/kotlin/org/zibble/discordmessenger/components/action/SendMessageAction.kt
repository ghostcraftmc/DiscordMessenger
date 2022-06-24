package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.readable.DiscordMessage
import java.util.concurrent.ThreadLocalRandom

class SendMessageAction(
    override val id: Long = ThreadLocalRandom.current().nextLong(),
    val channelId: Long,
    val message: DiscordMessage
) : Action(id) {

    companion object {
        fun of(channelId: Long, message: DiscordMessage) : SendMessageAction = SendMessageAction(
            channelId = channelId,
            message = message
        )
    }

    override fun getKey(): String = "sendMessage"

    override fun getName(): String = "Send Message"

}