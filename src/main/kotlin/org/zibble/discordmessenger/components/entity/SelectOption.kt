package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.JsonSerializable

class SelectOption(
    val label: String,
    val value: String,
    val description: String? = null,
    val default: Boolean = false,
    val emoji: Emoji? = null
) : JsonSerializable {

    companion object {
        const val LABEL_MAX_LENGTH = 100
        const val VALUE_MAX_LENGTH = 100
        const val DESCRIPTION_MAX_LENGTH = 100

        fun of(label: String, value: String) : SelectOption = SelectOption(label, value)
    }

    init {
        require(label.isNotEmpty()) { "Label must not be empty" }
        require(value.isNotEmpty()) { "Value must not be empty" }
        require(label.length <= LABEL_MAX_LENGTH) { "Label must not be longer than $LABEL_MAX_LENGTH characters" }
        require(value.length <= VALUE_MAX_LENGTH) { "Value must not be longer than $VALUE_MAX_LENGTH characters" }
        require((description?.length ?: 0) <= DESCRIPTION_MAX_LENGTH) { "Description must not be longer than $DESCRIPTION_MAX_LENGTH characters" }
    }

    fun withLabel(label: String): SelectOption = SelectOption(label, value, description, default, emoji)

    fun withValue(value: String): SelectOption = SelectOption(label, value, description, default, emoji)

    fun withDefault(default: Boolean) : SelectOption = SelectOption(label, value, description, default, emoji)

    fun withEmoji(emoji: Emoji?) : SelectOption = SelectOption(label, value, description, default, emoji)

}