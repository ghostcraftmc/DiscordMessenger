package org.zibble.discordmessenger.interaction.buttons

import org.zibble.discordmessenger.components.messagable.ButtonInteraction

interface ButtonAction {

    fun getId() : String

    fun execute(interaction: ButtonInteraction)

}