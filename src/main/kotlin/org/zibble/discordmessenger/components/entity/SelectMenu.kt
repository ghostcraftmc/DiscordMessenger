package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.Component

data class SelectMenu(
    val id: String,
    val placeholder: String? = null,
    val minValues: Int,
    val maxValues: Int,
    val disabled: Boolean = false,
    val options: List<SelectOption>
) : Component {

    companion object {
        const val ID_MAX_LENGTH = 100
        const val PLACEHOLDER_MAX_LENGTH = 100
        const val OPTIONS_MAX_AMOUNT = 25

        fun create(id: String) : Builder = Builder(id)
    }

    init {
        require(id.length <= ID_MAX_LENGTH) { "ID must be less than $ID_MAX_LENGTH characters" }
        require((placeholder?.length ?: 0) <= PLACEHOLDER_MAX_LENGTH) { "Placeholder must be less than $PLACEHOLDER_MAX_LENGTH characters" }
        require(options.size <= OPTIONS_MAX_AMOUNT) { "Options must be less than $OPTIONS_MAX_AMOUNT" }
    }

    override fun getType(): Component.Type = Component.Type.SELECT_MENU

    override fun withDisabled(disabled: Boolean): SelectMenu {
        return SelectMenu(id, placeholder, minValues, maxValues, disabled, options)
    }

    override fun isDisabled(): Boolean = disabled

    class Builder(customId: String) {

        var customId: String = customId
        private set
        var placeholder: String? = null
        private set
        var minValues = 1
        private set
        var maxValues = 1
        private set
        var isDisabled = false
        private set
        val options: MutableList<SelectOption> = ArrayList()

        fun setId(customId: String): Builder {
            require(customId.length <= ID_MAX_LENGTH) { "ID must be less than $ID_MAX_LENGTH characters" }
            this.customId = customId
            return this
        }

        fun setPlaceholder(placeholder: String?): Builder {
            require((placeholder?.length ?: 0) <= PLACEHOLDER_MAX_LENGTH) { "Placeholder must be less than $PLACEHOLDER_MAX_LENGTH characters" }
            this.placeholder = placeholder
            return this
        }

        fun setMinValues(minValues: Int): Builder {
            require(minValues > 0) { "Min values cannot be negative" }
            require(minValues <= OPTIONS_MAX_AMOUNT) { "Min Values may not be greater than $OPTIONS_MAX_AMOUNT! Provided: $minValues" }
            this.minValues = minValues
            return this
        }

        fun setMaxValues(maxValues: Int): Builder {
            require(maxValues > 0) { "Max values cannot be negative" }
            require(maxValues <= OPTIONS_MAX_AMOUNT) { "Max Values may not be greater than $OPTIONS_MAX_AMOUNT! Provided: $maxValues" }
            this.maxValues = maxValues
            return this
        }

        fun setRequiredRange(min: Int, max: Int): Builder {
            require(min <= max) { "Min Values should be less than or equal to Max Values! Provided: [$min, $max]" }
            return setMinValues(min).setMaxValues(max)
        }

        fun setDisabled(disabled: Boolean): Builder {
            isDisabled = disabled
            return this
        }

        fun addOptions(vararg options: SelectOption): Builder {
            require(this.options.size + options.size <= OPTIONS_MAX_AMOUNT) { "Cannot have more than $OPTIONS_MAX_AMOUNT options for a select menu!" }
            this.options.addAll(options)
            return this
        }

        fun addOptions(options: Collection<SelectOption>): Builder {
            require(this.options.size + options.size <= OPTIONS_MAX_AMOUNT) { "Cannot have more than $OPTIONS_MAX_AMOUNT options for a select menu!" }
            this.options.addAll(options)
            return this
        }

        fun addOption(label: String, value: String): Builder {
            return addOptions(SelectOption(label, value))
        }

        fun addOption(label: String, value: String, emoji: Emoji): Builder {
            return addOption(label, value, null, emoji)
        }

        fun addOption(label: String, value: String, description: String): Builder {
            return addOption(label, value, description, null)
        }

        fun addOption(label: String, value: String, description: String?, emoji: Emoji?): Builder {
            return addOptions(SelectOption(label, value, description, false, emoji))
        }

        fun setDefaultValues(values: Collection<String>): Builder {
            val set: Set<String> = HashSet(values)
            val it = options.listIterator()
            while (it.hasNext()) {
                val option = it.next()
                it.set(option.withDefault(set.contains(option.value)))
            }
            return this
        }

        fun setDefaultOptions(values: Collection<SelectOption>): Builder {
            return setDefaultValues(values.map { it.value })
        }

        fun build(): SelectMenu {
            require(minValues <= maxValues) { "Min values cannot be greater than max values!" }
            require(options.size <= OPTIONS_MAX_AMOUNT) { "Cannot build a select menu with more than $OPTIONS_MAX_AMOUNT options." }
            val min = minValues.coerceAtMost(options.size)
            val max = maxValues.coerceAtMost(options.size)
            return SelectMenu(customId, placeholder, min, max, isDisabled, options)
        }
    }

}

@Suppress("FunctionName")
fun SelectMenuBuilder(customId: String, builder: SelectMenu.Builder.() -> Unit): SelectMenu {
    return SelectMenu.create(customId).apply(builder).build()
}