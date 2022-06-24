package org.zibble.discordmessenger.interaction.buttons

import org.zibble.discordmessenger.components.messagable.ButtonInteraction
import java.util.concurrent.Executors

object ButtonFramework {

    private val buttonActions: MutableList<ButtonAction> = ArrayList()
    private val executor = Executors.newFixedThreadPool(2)

    fun registerButtonAction(action: ButtonAction) {
        buttonActions.add(action)
    }

    fun runAction(interaction: ButtonInteraction) {
        executor.submit {
            for (buttonAction in buttonActions) {
                if (buttonAction.getId() == interaction.button.custom_id) {
                    buttonAction.execute(interaction)
                    return@submit
                }
            }
        }
    }

}