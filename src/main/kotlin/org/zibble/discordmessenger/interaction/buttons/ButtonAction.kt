package org.zibble.discordmessenger.interaction.buttons

import org.zibble.discordmessenger.components.messagable.ButtonInteraction
import java.util.regex.Pattern

interface ButtonAction {

    fun getId() : Pattern

    fun execute(interaction: ButtonInteraction)

}