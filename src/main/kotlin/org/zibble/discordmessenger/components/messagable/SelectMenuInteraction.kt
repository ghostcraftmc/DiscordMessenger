package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.MessageChannel
import org.zibble.discordmessenger.components.entity.SelectMenu
import org.zibble.discordmessenger.components.entity.User
import org.zibble.discordmessenger.components.readable.DiscordMessage

class SelectMenuInteraction(
    val menu: SelectMenu,
    val user: User,
    val channel: MessageChannel,
    val messageId: Long
) : Replyable, JsonSerializable {

    override fun reply(message: DiscordMessage, ephermal: Boolean) = DiscordMessenger.replySelectMenu(menu, message, ephermal)

}