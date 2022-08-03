package org.zibble.discordmessenger.interaction.selectmenu

import org.zibble.discordmessenger.components.messagable.SelectMenuInteraction
import java.util.regex.Pattern

interface SelectMenuAction {

    fun getId() : Pattern

    fun execute(interaction: SelectMenuInteraction)
}