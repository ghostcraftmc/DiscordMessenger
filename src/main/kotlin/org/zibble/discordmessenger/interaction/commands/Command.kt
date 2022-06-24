package org.zibble.discordmessenger.interaction.commands

interface Command<T : org.zibble.discordmessenger.components.messagable.Command> {

    fun getSubCommands(): Array<SubCommand<T>>

    fun execute(command: T)

}