package org.zibble.discordmessenger.commands;

import org.zibble.discordmessenger.components.readable.ReceivedCommand;

public interface Command {

    SubCommand[] getSubCommands();

    void execute(ReceivedCommand command);

}
