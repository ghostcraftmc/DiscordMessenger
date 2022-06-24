package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.readable.DiscordMessage
import java.util.concurrent.ThreadLocalRandom

class SendMessageAction(
    val channelId: Long,
    val message: DiscordMessage
) : Action(ThreadLocalRandom.current().nextLong()) {

    companion object {
        fun of(channelId: Long, message: DiscordMessage) : SendMessageAction = SendMessageAction(
            channelId = channelId,
            message = message
        )
    }

    override fun getKey(): String = "sendMessage"

    override fun getName(): String = "Send Message"

}