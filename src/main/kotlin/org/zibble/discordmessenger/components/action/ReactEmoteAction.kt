package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.entity.Emoji
import java.util.concurrent.ThreadLocalRandom

data class ReactEmoteAction(
    val channelId: Long,
    val messageId: Long,
    val emote: Emoji
) : Action(ThreadLocalRandom.current().nextLong()) {

    companion object {
        fun of(channelId: Long, messageId: Long, emote: Emoji) : ReactEmoteAction = ReactEmoteAction(
            channelId,
            messageId,
            emote
        )
    }

    override fun getKey(): String = "reactEmote"

    override fun getName(): String = "React Emote"

}