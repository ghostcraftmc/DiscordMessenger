package org.zibble.discordmessenger.components.action

import java.util.concurrent.ThreadLocalRandom

class DeleteMessageAction(
    override val id: Long = ThreadLocalRandom.current().nextLong(),
    val channelId: Long,
    val messageId: Long
) : Action(id) {

    companion object {
        fun of(channelId: Long, messageId: Long) : DeleteMessageAction = DeleteMessageAction(
            channelId = channelId,
            messageId = messageId
        )
    }

    override fun getKey(): String = "deleteMessage"

    override fun getName(): String = "Delete Message"

}