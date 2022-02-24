package org.zibble.discordmessenger.commands;

import org.zibble.discordmessenger.components.readable.ReceivedCommand;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandFramework {

    private static CommandFramework instance;
    private final List<DiscordCommand> commands = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(6);

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
        executor.submit(() -> {
            for (DiscordCommand c : commands) {
                if (command.getContent().startsWith(c.getCommand())
                        || Arrays.stream(c.getAliases()).anyMatch(s -> command.getContent().startsWith(s))) {
                    try {
                        c.execute(command);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        });
    }

    public List<DiscordCommand> getCommands() {
        return commands;
    }

    public static CommandFramework getInstance() {
        return instance;
    }

}
