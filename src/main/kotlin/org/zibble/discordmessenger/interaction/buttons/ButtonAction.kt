package org.zibble.discordmessenger.interaction.buttons

import org.zibble.discordmessenger.components.messagable.ButtonInteraction

interface ButtonAction {

    fun getId() : Regex

    suspend fun execute(interaction: ButtonInteraction)

}