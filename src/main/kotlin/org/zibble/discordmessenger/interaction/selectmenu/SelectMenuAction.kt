package org.zibble.discordmessenger.interaction.selectmenu

import org.zibble.discordmessenger.components.messagable.SelectMenuInteraction

interface SelectMenuAction {

    fun getId() : String

    fun execute(interaction: SelectMenuInteraction)
}