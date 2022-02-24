package org.zibble.discordmessenger.commands;

import org.zibble.discordmessenger.components.readable.ReceivedCommand;

public interface DiscordCommand {

    String getCommand();

    default String[] getAliases() {
        return new String[0];
    }

    void execute(ReceivedCommand command);

}
