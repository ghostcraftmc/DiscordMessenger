package org.zibble.discordmessenger.interaction.selectmenu

import org.zibble.discordmessenger.components.messagable.SelectMenuInteraction

object SelectMenuFramework {

    private val selectMenuActions: MutableList<SelectMenuAction> = ArrayList()

    fun registerButtonAction(action: SelectMenuAction) {
        selectMenuActions.add(action)
    }

    suspend fun runAction(interaction: SelectMenuInteraction) {
        selectMenuActions.firstOrNull {
            it.getId().matches(interaction.menu.id)
        }?.execute(interaction)
    }

}