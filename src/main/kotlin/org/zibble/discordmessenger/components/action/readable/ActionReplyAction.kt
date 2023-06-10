package org.zibble.discordmessenger.components.action.readable

import com.google.gson.JsonObject
import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.action.ReadableAction
import org.zibble.discordmessenger.redis.RedisListener

class ActionReplyAction(
    id: Long,
    val actionId: Long,
    val content: JsonObject
) : ReadableAction(id, "actionReply", "Action Reply") {

    override suspend fun handle(discordMessenger: DiscordMessenger, redisListener: RedisListener): Boolean {
        RedisListener.waitingReply.getIfPresent(actionId)?.complete(this)
        RedisListener.waitingReply.invalidate(actionId)
        return true
    }

}