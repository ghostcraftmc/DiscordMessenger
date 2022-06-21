package org.zibble.discordmessenger.commands

interface DiscordCommand<T : org.zibble.discordmessenger.components.messagable.Command> : Command<T> {

    fun getCommand(): String

    fun getAliases(): Array<String> = arrayOf()

}