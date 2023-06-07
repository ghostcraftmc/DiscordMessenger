package org.zibble.discordmessenger.interaction.buttons

import org.zibble.discordmessenger.components.messagable.ButtonInteraction

object ButtonFramework {

    private val buttonActions: MutableList<ButtonAction> = ArrayList()

    fun registerButtonAction(action: ButtonAction) {
        buttonActions.add(action)
    }

    suspend fun runAction(interaction: ButtonInteraction) {
        buttonActions.firstOrNull {
            interaction.button.custom_id != null && it.getId().matches(interaction.button.custom_id)
        }?.execute(interaction)
    }

}