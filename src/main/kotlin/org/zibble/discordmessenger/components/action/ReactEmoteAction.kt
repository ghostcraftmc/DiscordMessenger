package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.entity.Emote
import java.util.concurrent.ThreadLocalRandom

class ReactEmoteAction(
    override val id: Long = ThreadLocalRandom.current().nextLong(),
    val channelId: Long,
    val messageId: Long,
    val emote: Emote
) : Action(id) {

    companion object {
        fun of(channelId: Long, messageId: Long, emote: Emote) : ReactEmoteAction = ReactEmoteAction(
            channelId = channelId,
            messageId = messageId,
            emote = emote
        )
    }

    override fun getKey(): String = "reactEmote"

    override fun getName(): String = "React Emote"

}