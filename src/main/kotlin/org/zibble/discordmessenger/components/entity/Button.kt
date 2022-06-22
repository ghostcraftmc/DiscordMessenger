package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.Component

class Button(
    val style: ButtonStyle,
    val label: String = "",
    val custom_id: String? = null,
    val url: String? = null,
    val disabled: Boolean = false,
    val emoji: Emoji? = null,
    @Transient val interaction: (User, MessageChannel) -> Unit = { _, _ -> }
) : Component {

    var shouldSendInteraction: Boolean = false

    companion object {
        const val LABEL_MAX_LENGTH = 80
        const val ID_MAX_LENGTH = 100
        const val URL_MAX_LENGTH = 512

        fun link(url: String, label: String) : Button {
            return Button(
                style = ButtonStyle.LINK,
                label = label,
                url = url
            )
        }

        fun link(url: String, emoji: Emoji) : Button {
            return Button(
                style = ButtonStyle.LINK,
                emoji = emoji,
                url = url
            )
        }

        fun of(style: ButtonStyle, idOrUrl: String, label: String) : Button {
            return if (style == ButtonStyle.LINK) {
                link(idOrUrl, label)
            } else {
                Button(
                    style = style,
                    custom_id = idOrUrl,
                    label = label
                )
            }
        }

        fun of(style: ButtonStyle, idOrUrl: String, emoji: Emoji) : Button {
            return if (style == ButtonStyle.LINK) {
                link(idOrUrl, emoji)
            } else {
                Button(
                    style = style,
                    custom_id = idOrUrl,
                    emoji = emoji
                )
            }
        }
    }

    init {
        require(label.length <= LABEL_MAX_LENGTH) {
            "Label must be less than $LABEL_MAX_LENGTH characters"
        }
        require((custom_id?.length ?: 0) <= ID_MAX_LENGTH) {
            "Custom ID must be less than $ID_MAX_LENGTH characters"
        }
        require((url?.length ?: 0) <= URL_MAX_LENGTH) {
            "URL must be less than $URL_MAX_LENGTH characters"
        }
    }

    override fun getType(): Component.Type = Component.Type.BUTTON

    fun withLabel(label: String) : Button {
        return Button(
            style = style,
            label = label,
            custom_id = custom_id,
            url = url,
            disabled = disabled,
            emoji = emoji
        )
    }

    fun withEmoji(emoji: Emoji?) : Button {
        return Button(
            style = style,
            label = label,
            custom_id = custom_id,
            url = url,
            disabled = disabled,
            emoji = emoji
        )
    }

    fun withCustomId(custom_id: String?) : Button {
        return Button(
            style = style,
            label = label,
            custom_id = custom_id,
            url = url,
            disabled = disabled,
            emoji = emoji
        )
    }

    fun withUrl(url: String?) : Button {
        return Button(
            style = style,
            label = label,
            custom_id = custom_id,
            url = url,
            disabled = disabled,
            emoji = emoji
        )
    }

    override fun withDisabled(disabled: Boolean) : Button {
        return Button(
            style = style,
            label = label,
            custom_id = custom_id,
            url = url,
            disabled = disabled,
            emoji = emoji
        )
    }

    fun withStyle(style: ButtonStyle) : Button {
        return Button(
            style = style,
            label = label,
            custom_id = custom_id,
            url = url,
            disabled = disabled,
            emoji = emoji
        )
    }

    fun withInteraction(interaction: (User, MessageChannel) -> Unit) : Button {
        return Button(
            style = style,
            label = label,
            custom_id = custom_id,
            url = url,
            disabled = disabled,
            emoji = emoji,
            interaction = interaction
        ).also { it.shouldSendInteraction = true }
    }

    override fun isDisabled(): Boolean = disabled

}