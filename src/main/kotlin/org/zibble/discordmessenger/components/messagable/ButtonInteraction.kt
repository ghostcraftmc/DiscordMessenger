package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.Button
import org.zibble.discordmessenger.components.entity.MessageChannel
import org.zibble.discordmessenger.components.entity.User
import org.zibble.discordmessenger.components.readable.DiscordMessage

class ButtonInteraction(
    val button: Button,
    val user: User,
    val channel: MessageChannel,
    val messageId: Long
) : Replyable, JsonSerializable {
    override fun reply(message: DiscordMessage, ephermal: Boolean) = DiscordMessenger.replyButton(button, message, ephermal)

}