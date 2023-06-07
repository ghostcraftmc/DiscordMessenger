package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.entity.ActionRow
import org.zibble.discordmessenger.components.messagable.MentionType
import java.util.*
import kotlin.collections.ArrayList

data class DiscordMessage(
    val content : String? = null,
    val embeds : Collection<DiscordEmbed> = emptyList(),
    var isTTS : Boolean = false,
    val allowedMentions : Collection<MentionType> = setOf(MentionType.EVERYONE),
    val actionRow : Collection<ActionRow> = emptyList()
) : JsonSerializable {

    companion object {
        const val MAX_CONTENT_LENGTH = 2000
        const val MAX_FILES = 10
        const val MAX_EMBEDS = 10

        fun embeds(first: DiscordEmbed, vararg embeds: DiscordEmbed): DiscordMessage {
            require(embeds.size <= MAX_EMBEDS) { "Cannot add more than $MAX_EMBEDS embeds to a message" }
            val list: MutableList<DiscordEmbed> = ArrayList(1 + embeds.size)
            list.add(first)
            list.addAll(embeds)
            return DiscordMessage(embeds = list)
        }

        fun embeds(embeds: Collection<DiscordEmbed>): DiscordMessage {
            require(embeds.size < MAX_EMBEDS) { "Cannot add more than 10 embeds to a message" }
            require(embeds.isNotEmpty()) { "Cannot build an empty message" }
            return DiscordMessage(embeds = embeds)
        }

        fun text(text: String) : DiscordMessage = DiscordMessage(text)

        fun builder() : Builder = Builder()
    }

    init {
        require((content?.length ?: 0) < MAX_CONTENT_LENGTH) { "Content may not exceed 2000 characters!" }
        require(embeds.size < MAX_EMBEDS) { "Cannot add more than 10 embeds to a message" }
    }

    class Builder {

        private val content = StringBuilder()
        private val embeds: MutableList<DiscordEmbed> = LinkedList()
        private var isTTS = false
        private val allowedMentions: MutableCollection<MentionType> = ArrayList()
        private val actionRow: MutableCollection<ActionRow> = ArrayList()
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

        fun content(content: String?): Builder {
            require((content?.length ?: 0) < MAX_CONTENT_LENGTH) { "Content may not exceed 2000 characters!" }
            this.content.setLength(0)
            if (!content.isNullOrEmpty()) {
                this.content.append(content)
            }
            return this
        }

        fun appendContent(content: String): Builder {
            require(this.content.length + content.length < MAX_CONTENT_LENGTH) { "Content may not exceed 2000 characters!" }
            this.content.append(content)
            return this
        }

        fun embed(vararg embeds: DiscordEmbed): Builder {
            return this.embeds(embeds.toList())
        }

        fun embeds(embeds: Collection<DiscordEmbed>): Builder {
            require(this.embeds.size + embeds.size < MAX_EMBEDS) { "Cannot add more than $MAX_EMBEDS embeds to a message" }
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

        fun actionRow(vararg row: ActionRow) : Builder {
            return this.actionRow(row.toList())
        }

        fun actionRow(row: Collection<ActionRow>) : Builder {
            actionRow.addAll(row)
            return this
        }

        fun build(): DiscordMessage {
            return DiscordMessage(content.toString(), embeds, isTTS, allowedMentions, actionRow)
        }
    }

}

@Suppress("FunctionName")
fun DiscordMessageBuilder(block: DiscordMessage.Builder.() -> Unit) : DiscordMessage {
    return DiscordMessage.Builder().apply(block).build()
}