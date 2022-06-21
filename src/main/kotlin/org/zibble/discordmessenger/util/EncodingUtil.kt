package org.zibble.discordmessenger.util

object EncodingUtil {

    fun decodeCodepoint(codepoint: String): String {
        require(codepoint.startsWith("U+")) { "Invalid format" }
        return decodeCodepoint(codepoint.substring(2), 16)
    }

    private fun decodeCodepoint(hex: String, radix: Int): String {
        val codePoint = Integer.parseUnsignedInt(hex, radix)
        return String(Character.toChars(codePoint))
    }

}