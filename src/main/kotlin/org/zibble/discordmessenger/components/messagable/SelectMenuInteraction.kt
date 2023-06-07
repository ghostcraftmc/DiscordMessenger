package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.MessageChannel
import org.zibble.discordmessenger.components.entity.SelectMenu
import org.zibble.discordmessenger.components.entity.SelectOption
import org.zibble.discordmessenger.components.entity.User
import org.zibble.discordmessenger.components.readable.DiscordMessage

data class SelectMenuInteraction(
    val menu: SelectMenu,
    val user: User,
    val channel: MessageChannel,
    val messageId: Long,
    val selectedOptions: List<SelectOption>
) : Replyable, JsonSerializable {

    override suspend fun reply(message: DiscordMessage, ephemeral: Boolean) = DiscordMessenger.replySelectMenu(menu, message, ephemeral)

}