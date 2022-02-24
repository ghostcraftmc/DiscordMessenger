package org.zibble.discordmessenger.commands;

import org.zibble.discordmessenger.components.readable.ReceivedCommand;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandFramework {

    private static CommandFramework instance;
    private final List<DiscordCommand> commands = new CopyOnWriteArrayList<>();

    public CommandFramework() {
        instance = this;
    }

    public void registerCommand(DiscordCommand command) {
        this.commands.add(command);
    }

    public void unregisterCommand(DiscordCommand command) {
        this.commands.remove(command);
    }

    public void runCommand(ReceivedCommand command) {
        for (DiscordCommand c : commands) {
            if (command.getContent().startsWith(c.getCommand())
                    || Arrays.stream(c.getAliases()).anyMatch(s -> command.getContent().startsWith(s))) {
                c.execute(command);
                break;
            }
        }
    }

    public List<DiscordCommand> getCommands() {
        return commands;
    }

    public static CommandFramework getInstance() {
        return instance;
    }

}
