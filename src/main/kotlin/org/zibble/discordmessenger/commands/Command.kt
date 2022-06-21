package org.zibble.discordmessenger.commands

interface Command<T : org.zibble.discordmessenger.components.messagable.Command> {

    fun getSubCommands(): Array<SubCommand<T>>

    fun execute(command: T)

}