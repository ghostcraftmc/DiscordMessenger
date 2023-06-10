package org.zibble.discordmessenger.components.action.readable

import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.action.ReadableAction
import org.zibble.discordmessenger.components.entity.User
import org.zibble.discordmessenger.components.readable.DiscordMessage
import org.zibble.discordmessenger.redis.RedisListener

class ChannelMessageAction(
    id: Long,
    val channelId: Long,
    val user: User,
    val message: DiscordMessage
) : ReadableAction(id, "channelMessageAction", "Channel Message") {

    companion object {
        private val LISTENERS: MutableSet<DiscordMessageListener> = mutableSetOf()

        fun registerListener(listener: DiscordMessageListener) {
            LISTENERS.add(listener)
        }

        fun unregisterListener(listener: DiscordMessageListener) {
            LISTENERS.remove(listener)
        }
    }

    override suspend fun handle(discordMessenger: DiscordMessenger, redisListener: RedisListener): Boolean {
        LISTENERS.forEach {
            it.onDiscordMessage(channelId, user, message)
        }
        return true
    }

}

interface DiscordMessageListener {

    suspend fun onDiscordMessage(channelId: Long, user: User, message: DiscordMessage)

}