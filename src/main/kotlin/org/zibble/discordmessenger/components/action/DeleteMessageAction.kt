package org.zibble.discordmessenger.components.action

import java.util.concurrent.ThreadLocalRandom

class DeleteMessageAction(
    val channelId: Long,
    val messageId: Long
) : Action(ThreadLocalRandom.current().nextLong()) {

    companion object {
        fun of(channelId: Long, messageId: Long) : DeleteMessageAction = DeleteMessageAction(
            channelId = channelId,
            messageId = messageId
        )
    }

    override fun getKey(): String = "deleteMessage"

    override fun getName(): String = "Delete Message"

}