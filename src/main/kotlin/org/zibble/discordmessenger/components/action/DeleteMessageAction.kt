package org.zibble.discordmessenger.components.action

class DeleteMessageAction(
    val channelId: Long,
    val messageId: Long
) : Action() {

    companion object {
        fun of(channelId: Long, messageId: Long) : DeleteMessageAction = DeleteMessageAction(channelId, messageId)
    }

    override fun getKey(): String = "deleteMessage"

    override fun getName(): String = "Delete Message"

}