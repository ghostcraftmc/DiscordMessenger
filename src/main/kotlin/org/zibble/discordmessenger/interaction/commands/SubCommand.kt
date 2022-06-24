package org.zibble.discordmessenger.interaction.commands

interface SubCommand<T : org.zibble.discordmessenger.components.messagable.Command> : Command<T> {

    fun name() : String

}