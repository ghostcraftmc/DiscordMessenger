package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.MessageChannel
import org.zibble.discordmessenger.components.entity.User
import org.zibble.discordmessenger.components.readable.DiscordMessage
import java.time.OffsetDateTime

interface Command : JsonSerializable {

    fun getId() : Long

    fun getType() : CommandType

    fun getChannel() : MessageChannel

    fun getUser() : User

    fun getSentTime() : OffsetDateTime

    fun getPublicPermission() : Long

    fun reply(message: DiscordMessage, ephermal: Boolean = false) = DiscordMessenger.replyCommand(getId(), message, ephermal)

}