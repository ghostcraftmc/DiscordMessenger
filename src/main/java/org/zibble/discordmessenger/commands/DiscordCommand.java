package org.zibble.discordmessenger.commands;

public interface DiscordCommand extends Command {

    String getCommand();

    default String[] getAliases() {
        return new String[0];
    }

}
