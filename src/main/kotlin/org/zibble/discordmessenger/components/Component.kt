package org.zibble.discordmessenger.components

interface Component : JsonSerializable {

    fun getType() : Type

    fun withDisabled(disabled : Boolean) : Component

    fun asDisabled(): Component = withDisabled(true)

    fun asEnabled(): Component = withDisabled(false)

    fun isDisabled(): Boolean

    fun isEnabled(): Boolean = !isDisabled()

    enum class Type(val id: Int, val maxPerRow: Int) {
        ACTION_ROW(1, 0),
        BUTTON(2, 5),
        SELECT_MENU(3, 1),
        ;
    }

}