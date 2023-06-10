package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.action.sendable.*
import org.zibble.discordmessenger.components.action.readable.*
import org.zibble.discordmessenger.redis.RedisListener
import java.util.concurrent.ThreadLocalRandom

abstract class Action(
    val id: Long,
    @Transient val key: String,
    @Transient val name: String
) : JsonSerializable {

    companion object {
        val actionTypes = mapOf(
            // Readable actions
            "assignRole" to AssignRoleAction::class.java,
            "deleteMessage" to DeleteMessageAction::class.java,
            "editMessage" to EditMessageAction::class.java,
            "reactEmote" to ReactEmoteAction::class.java,
            "removeRole" to RemoveRoleAction::class.java,
            "replyMessage" to ReplyMessageAction::class.java,
            "sendMessage" to SendMessageAction::class.java,
            "sendWebhookMessage" to SendWebhookMessageAction::class.java,

            // Sendable actions
            "actionReply" to ActionReplyAction::class.java,
            "channelMessageAction" to ChannelMessageAction::class.java
        )

        val readableActions = actionTypes.filter { it.value.superclass == ReadableAction::class.java }
        val sendableActions = actionTypes.filter { it.value.superclass == SendableAction::class.java }
    }

    abstract suspend fun handle(discordMessenger: DiscordMessenger, redisListener: RedisListener): Boolean

}

abstract class ReadableAction(
    id: Long,
    key: String,
    name: String
) : Action(id, key, name)

abstract class SendableAction(
    key: String,
    name: String
) : Action(ThreadLocalRandom.current().nextLong(), key, name) {

    override suspend fun handle(discordMessenger: DiscordMessenger, redisListener: RedisListener): Boolean {
        throw UnsupportedOperationException("SendableAction cannot be handled.")
    }

}