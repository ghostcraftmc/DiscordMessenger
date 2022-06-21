package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.entity.Emote

class ReactEmoteAction(
    val channelId: Long,
    val messageId: Long,
    val emote: Emote
) : Action() {

    companion object {
        fun of(channelId: Long, messageId: Long, emote: Emote) : ReactEmoteAction = ReactEmoteAction(channelId, messageId, emote)
    }

    override fun getKey(): String = "reactEmote"

    override fun getName(): String = "React Emote"

}