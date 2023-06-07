package org.zibble.discordmessenger.components.readable

import java.awt.Color
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAccessor


data class DiscordEmbed(
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
        const val TITLE_MAX_LENGTH = 256
        const val AUTHOR_MAX_LENGTH = 256
        const val VALUE_MAX_LENGTH = 1024
        const val DESCRIPTION_MAX_LENGTH = 4096
        const val TEXT_MAX_LENGTH = 2048
        const val URL_MAX_LENGTH = 2000
        const val MAX_FIELDS = 25

        val URL_PATTERN = Regex("\\s*(https?|attachment)://\\S+\\s*", RegexOption.IGNORE_CASE).toPattern()

        fun builder() : Builder = Builder()

        private fun urlCheck(url: String?) {
            if (url != null) {
                require(url.length <= URL_MAX_LENGTH) { "URL cannot be longer than $URL_MAX_LENGTH characters." }
                require(URL_PATTERN.matcher(url).matches()) { "URL must be a valid http(s) or attachment url." }
            }
        }
    }

    init {
        require((description?.length ?: 0) <= DESCRIPTION_MAX_LENGTH) { "Description is longer than $DESCRIPTION_MAX_LENGTH! Please limit your input!" }
        urlCheck(thumbnailUrl)
        urlCheck(imageUrl)
        require(fields.size <= MAX_FIELDS) { "Fields cannot be more than $MAX_FIELDS" }
    }

    data class EmbedTitle(val text: String, val url: String?) {

        init {
            require(text.isNotEmpty()) { "Title test cannot be empty!" }
            require(text.length <= TITLE_MAX_LENGTH) { "Title cannot be longer than $TITLE_MAX_LENGTH characters." }
            urlCheck(url)
        }

    }

    data class EmbedFooter(val text: String, val icon: String?) {

        init {
            require(text.length <= TEXT_MAX_LENGTH) { "Text cannot be longer than $TEXT_MAX_LENGTH characters." }
            urlCheck(icon)
        }

    }

    data class EmbedAuthor(val name: String, val iconUrl: String?, val url: String?) {

        init {
            require(name.length <= AUTHOR_MAX_LENGTH) { "Name cannot be longer than $AUTHOR_MAX_LENGTH characters." }
            urlCheck(iconUrl)
            urlCheck(url)
        }

    }

    data class EmbedField(val name: String, val value: String, val isInline: Boolean = false) {

        init {
            require(name.length <= TITLE_MAX_LENGTH) { "Name cannot be longer than $TITLE_MAX_LENGTH characters." }
            require(value.length <= VALUE_MAX_LENGTH) { "Value cannot be longer than $VALUE_MAX_LENGTH characters." }
        }

    }

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

@Suppress("FunctionName")
fun DiscordEmbedBuilder(block: DiscordEmbed.Builder.() -> Unit): DiscordEmbed {
    return DiscordEmbed.builder().apply(block).build()
}