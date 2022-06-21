package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.util.EncodingUtil

class Emoji(
    val name: String,
    val id: Long,
    val animated: Boolean
) : JsonSerializable {

    companion object {

        private val PATTERN = Regex("<a?:(\\w+):(\\d+)>").toPattern()

        fun fromUnicode(code: String): Emoji {
            var unicode = code
            if (unicode.startsWith("U+") || unicode.startsWith("u+")) {
                val emoji = StringBuilder()
                val codepoints =
                    unicode.trim { it <= ' ' }.split("\\s*[uU]\\+".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                for (codepoint in codepoints) emoji.append(
                    if (codepoint.isEmpty()) "" else EncodingUtil.decodeCodepoint(
                        "U+$codepoint"
                    )
                )
                unicode = emoji.toString()
            }
            return Emoji(unicode, 0, false)
        }

        fun fromEmote(name: String, id: Long, animated: Boolean): Emoji {
            return Emoji(name, id, animated)
        }

        fun fromMarkdown(code: String): Emoji {
            val matcher = PATTERN.matcher(code)
            return if (matcher.matches()) fromEmote(
                matcher.group(1),
                java.lang.Long.parseUnsignedLong(matcher.group(2)),
                code.startsWith("<a")
            ) else fromUnicode(code)
        }
    }

    fun isUnicode() = id == 0L

    fun isCustom() = !isUnicode()

}