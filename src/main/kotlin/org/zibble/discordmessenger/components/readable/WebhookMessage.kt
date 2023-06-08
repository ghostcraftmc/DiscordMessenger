package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.messagable.MentionType
import java.util.*

data class WebhookMessage(
    val username: String? = null,
    val avatarUrl: String? = null,
    val content: String? = null,
    val embeds: Collection<DiscordEmbed> = emptyList(),
    val isTTs: Boolean = false,
    val allowedMessage: Collection<MentionType> = setOf(MentionType.EVERYONE)
) : JsonSerializable {

    companion object {
        const val MAX_CONTENT_LENGTH = 2000
        const val MAX_FILES = 10
        const val MAX_EMBEDS = 10

        fun embeds(first: DiscordEmbed, vararg embeds: DiscordEmbed): WebhookMessage {
            require(embeds.size <= MAX_EMBEDS) { "Cannot add more than $MAX_EMBEDS embeds to a message" }
            val list: MutableList<DiscordEmbed> = ArrayList(1 + embeds.size)
            list.add(first)
            list.addAll(embeds)
            return WebhookMessage(embeds = list)
        }

        fun embeds(embeds: Collection<DiscordEmbed>): WebhookMessage {
            require(embeds.size < MAX_EMBEDS) { "Cannot add more than 10 embeds to a message" }
            require(embeds.isNotEmpty()) { "Cannot build an empty message" }
            return WebhookMessage(embeds = embeds)
        }

        fun text(text: String) : WebhookMessage = WebhookMessage(text)

        fun builder() : Builder = Builder()
    }

    init {
        require((content?.length ?: 0) < MAX_CONTENT_LENGTH) { "Content may not exceed 2000 characters!" }
        require(embeds.size < MAX_EMBEDS) { "Cannot add more than 10 embeds to a message" }
    }

    class Builder {

        private var username: String? = null
        private var avatarUrl: String? = null
        private val content = StringBuilder()
        private val embeds: MutableList<DiscordEmbed> = LinkedList()
        private var isTTS = false
        private val allowedMentions: MutableCollection<MentionType> = ArrayList()

        init {
            allowedMentions.add(MentionType.EVERYONE)
        }

        val isEmpty: Boolean
            get() = content.isEmpty() && embeds.isEmpty()

        fun reset(): Builder {
            content.setLength(0)
            resetEmbeds()
            isTTS = false
            return this
        }

        fun resetEmbeds(): Builder {
            embeds.clear()
            return this
        }

        fun username(username: String?): Builder {
            this.username = username
            return this
        }

        fun avatarUrl(avatarUrl: String?): Builder {
            this.avatarUrl = avatarUrl
            return this
        }

        fun content(content: String?): Builder {
            require((content?.length ?: 0) < DiscordMessage.MAX_CONTENT_LENGTH) { "Content may not exceed 2000 characters!" }
            this.content.setLength(0)
            if (!content.isNullOrEmpty()) {
                this.content.append(content)
            }
            return this
        }

        fun appendContent(content: String): Builder {
            require(this.content.length + content.length < DiscordMessage.MAX_CONTENT_LENGTH) { "Content may not exceed 2000 characters!" }
            this.content.append(content)
            return this
        }

        fun embed(vararg embeds: DiscordEmbed): Builder {
            return this.embeds(embeds.toList())
        }

        fun embeds(embeds: Collection<DiscordEmbed>): Builder {
            require(this.embeds.size + embeds.size < DiscordMessage.MAX_EMBEDS) { "Cannot add more than ${DiscordMessage.MAX_EMBEDS} embeds to a message" }
            this.embeds.addAll(embeds)
            return this
        }

        fun tts(isTTS: Boolean): Builder {
            this.isTTS = isTTS
            return this
        }

        fun allowedMentions(vararg mentionTypes: MentionType): Builder {
            return this.allowedMentions(mentionTypes.toList())
        }

        fun allowedMentions(mentionTypes: Collection<MentionType>): Builder {
            allowedMentions.addAll(mentionTypes)
            return this
        }

        fun build(): WebhookMessage {
            return WebhookMessage(username, avatarUrl, content.toString(), embeds, isTTS, allowedMentions)
        }
    }

}