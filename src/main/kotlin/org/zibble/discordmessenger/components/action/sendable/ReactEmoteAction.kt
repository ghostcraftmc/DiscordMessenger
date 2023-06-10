package org.zibble.discordmessenger.components.action.sendable

import org.zibble.discordmessenger.components.action.SendableAction
import org.zibble.discordmessenger.components.entity.Emoji

data class ReactEmoteAction(
    val channelId: Long,
    val messageId: Long,
    val emote: Emoji
) : SendableAction("reactEmote", "React Emote") {

    companion object {
        fun of(channelId: Long, messageId: Long, emote: Emoji) : ReactEmoteAction = ReactEmoteAction(
            channelId,
            messageId,
            emote
        )
    }

}