package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.redis.RedisListener

class ButtonInteraction(
    val buttonId: String,
    val user: User,
    val channel: MessageChannel
) : JsonSerializable {

    fun handleInteraction() {
        RedisListener.intractableButtons.forEach { (id, fn) ->
            if (id == buttonId) {
                fn(user, channel)
                return@forEach
            }
        }
    }

}