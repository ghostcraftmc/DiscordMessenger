package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.MessageChannel
import org.zibble.discordmessenger.components.entity.User
import org.zibble.discordmessenger.components.readable.DiscordMessage
import java.time.OffsetDateTime

interface Command : Replyable, JsonSerializable {

    fun getId() : Long

    fun getType() : CommandType

    fun getChannel() : MessageChannel

    fun getUser() : User

    fun getSentTime() : OffsetDateTime

    fun getPublicPermission() : Long

    override suspend fun reply(message: DiscordMessage, ephemeral: Boolean) = DiscordMessenger.replyCommand(getId(), message, ephemeral)

}