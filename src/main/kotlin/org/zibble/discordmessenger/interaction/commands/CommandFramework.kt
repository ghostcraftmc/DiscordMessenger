package org.zibble.discordmessenger.interaction.commands

import org.zibble.discordmessenger.components.messagable.LegacyCommand
import org.zibble.discordmessenger.components.messagable.SlashCommand
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

object CommandFramework {

    private val legacyCommands: MutableList<DiscordCommand<LegacyCommand>> = CopyOnWriteArrayList()
    private val commands: MutableList<DiscordCommand<SlashCommand>> = CopyOnWriteArrayList()

    fun registerLegacyCommand(command: DiscordCommand<LegacyCommand>) {
        legacyCommands.add(command)
    }

    fun unregisterLegacyCommand(command: DiscordCommand<LegacyCommand>) {
        legacyCommands.remove(command)
    }

    fun registerSlashCommand(command: DiscordCommand<SlashCommand>) {
        commands.add(command)
    }

    fun unregisterSlashCommand(command: DiscordCommand<SlashCommand>) {
        commands.remove(command)
    }

    suspend fun runCommand(command: org.zibble.discordmessenger.components.messagable.Command) {
        if (command is LegacyCommand) {
            legacyCommands.filter {
                command.content.startsWith(it.getCommand(), true) || it.getAliases()
                    .any { alias -> command.content.startsWith(alias, true) }
            }.forEach {
                var cmd: Command<LegacyCommand> = it
                val args: List<String> = command.content.trim().split(" ")
                if (args.size > 1 && it.getSubCommands().isNotEmpty()) {
                    val i = AtomicInteger(1)
                    while (true) {
                        val first = cmd.getSubCommands().firstOrNull { s -> s.name().equals(args[i.get()], true) }
                        if (first != null) {
                            cmd = first
                            if (i.incrementAndGet() >= args.size) break
                        } else {
                            break
                        }
                    }
                }
                cmd.execute(command)
            }
        } else {
            command as SlashCommand
            commands.filter {
                command.name.startsWith(it.getCommand(), true) || it.getAliases()
                    .any { alias -> command.name.startsWith(alias, true) }
            }.forEach {
                it.execute(command)
            }
        }
    }
}