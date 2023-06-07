package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.Component

class ActionRow(
    components: List<Component>
) : Component {

    private val components = components.toMutableList()

    companion object {
        fun of(vararg components: Component): ActionRow {
            return ActionRow(components.toList())
        }

        fun of(components: List<Component>): ActionRow {
            return ActionRow(components)
        }
    }

    init {
        require(!isEmpty()) {
            "ActionRow must contain at least one component"
        }
        require(isValid()) {
            "ActionRow can only contain 5 buttons or 1 select menu"
        }
    }

    fun isEmpty(): Boolean = components.isEmpty()

    fun isValid() : Boolean {
        if (isEmpty()) return false
        if (components.map { it.getType() }.groupingBy { it }.eachCount().size > 1) return false
        val type = components.first().getType()
        return components.size <= type.maxPerRow
    }

    fun getComponents() : List<Component> = components.toList()

    override fun getType(): Component.Type = Component.Type.ACTION_ROW

    override fun withDisabled(disabled: Boolean): ActionRow {
        return ActionRow(components.map { it.withDisabled(disabled) })
    }

    fun updateComponent(id: String, newComponent: Button?): Button? {
        if (components.first() !is Button) return null
        val it: MutableListIterator<Component> = components.listIterator()
        while (it.hasNext()) {
            val button = it.next() as Button
            if (id == button.custom_id || id == button.url) {
                if (newComponent == null) it.remove() else it.set(newComponent)
                return button
            }
        }
        return null
    }

    override fun isDisabled() : Boolean = components.all { it.isDisabled() }

    override fun isEnabled(): Boolean = components.all { it.isEnabled() }

}