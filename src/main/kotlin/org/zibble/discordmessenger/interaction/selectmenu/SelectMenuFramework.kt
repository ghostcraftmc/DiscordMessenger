package org.zibble.discordmessenger.interaction.selectmenu

import org.zibble.discordmessenger.components.messagable.SelectMenuInteraction
import java.util.concurrent.Executors

object SelectMenuFramework {

    private val selectMenuActions: MutableList<SelectMenuAction> = ArrayList()
    private val executor = Executors.newFixedThreadPool(2)

    fun registerButtonAction(action: SelectMenuAction) {
        selectMenuActions.add(action)
    }

    fun runAction(interaction: SelectMenuInteraction) {
        executor.submit {
            for (buttonAction in selectMenuActions) {
                if (buttonAction.getId() == interaction.menu.id) {
                    buttonAction.execute(interaction)
                    return@submit
                }
            }
        }
    }

}