package org.zibble.discordmessenger.components.readable

import java.awt.Color
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAccessor


class DiscordEmbed(
    val timestamp: OffsetDateTime?,
    val color: Color?,
    val description: String?,
    val thumbnailUrl: String?,
    val imageUrl: String?,
    val footer: EmbedFooter?,
    val title: EmbedTitle?,
    val author: EmbedAuthor?,
    val fields: Collection<EmbedField>
) {

    companion object {
        const val MAX_FIELDS = 25

        fun builder() : Builder = Builder()
    }

    init {
        require(fields.size <= MAX_FIELDS) { "Fields cannot be more than $MAX_FIELDS" }
    }

    class EmbedTitle(val text: String, val url: String?)

    class EmbedFooter(val text: String, val icon: String?)

    class EmbedAuthor(val name: String, val iconUrl: String?, val url: String?)

    class EmbedField(val name: String, val value: String, val isInline: Boolean = false)

    class Builder {

        private var timestamp: OffsetDateTime? = null
        private var color: Color? = null
        private var description: String? = null
        private var thumbnailUrl: String? = null
        private var imageUrl: String? = null
        private var footer: EmbedFooter? = null
        private var title: EmbedTitle? = null
        private var author: EmbedAuthor? = null
        private val fields: MutableList<EmbedField> = ArrayList()

        fun timestamp(timestamp: TemporalAccessor?): Builder {
            if (timestamp is Instant) {
                this.timestamp = OffsetDateTime.ofInstant(timestamp as Instant?, ZoneId.of("UTC"))
            } else {
                this.timestamp = if (timestamp == null) null else OffsetDateTime.from(timestamp)
            }
            return this
        }

        fun color(color: Color?): Builder {
            this.color = color
            return this
        }

        fun description(description: String?): Builder {
            this.description = description
            return this
        }

        fun thumbnailUrl(thumbnailUrl: String?): Builder {
            this.thumbnailUrl = thumbnailUrl
            return this
        }

        fun imageUrl(imageUrl: String?): Builder {
            this.imageUrl = imageUrl
            return this
        }

        fun footer(footer: EmbedFooter?): Builder {
            this.footer = footer
            return this
        }

        fun title(title: EmbedTitle?): Builder {
            this.title = title
            return this
        }

        fun author(author: EmbedAuthor?): Builder {
            this.author = author
            return this
        }

        fun field(field: EmbedField): Builder {
            return if (fields.size == 25) {
                throw IllegalStateException("Cannot add more than 25 fields")
            } else {
                fields.add(field)
                this
            }
        }

        fun field(fields: Collection<EmbedField>?): Builder {
            return if (this.fields.size == 25) {
                throw IllegalStateException("Cannot add more than 25 fields")
            } else {
                this.fields.addAll(fields!!)
                this
            }
        }

        fun build(): DiscordEmbed = DiscordEmbed(timestamp, color, description, thumbnailUrl, imageUrl, footer, title, author, fields)
    }

}