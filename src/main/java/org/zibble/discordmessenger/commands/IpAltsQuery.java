package org.zibble.discordmessenger.commands;

import litebans.api.Database;
import org.bukkit.Bukkit;
import org.zibble.discordmessenger.DiscordMessenger;
import org.zibble.discordmessenger.components.Permission;
import org.zibble.discordmessenger.components.messagable.DiscordEmbed;
import org.zibble.discordmessenger.components.readable.ReceivedCommand;

import java.awt.*;
import java.util.Collection;
import java.util.UUID;

public class IpAltsQuery implements DiscordCommand{

    @Override
    public String getCommand() {
        return "-scan";
    }

    @Override
    public void execute(ReceivedCommand command) {
        if (command.getUser() != null && command.getUser().hasPermission(Permission.ADMINISTRATOR)) {
            String playerIP = command.getContent().replace(this.getCommand(), "").trim();
            if (playerIP.isEmpty()) {
                DiscordEmbed error = DiscordEmbed.builder()
                        .author(new DiscordEmbed.EmbedAuthor("Invalid Usage !","https://cdn.discordapp.com/attachments/836464907984568330/941049724386021436/PikPng.com_metal-gear-solid-exclamation_2738816.png",null))
                        .title(new DiscordEmbed.EmbedTitle("Proper Usage", null))
                        .description("Command: `-scan <playerIP>`")
                        .color(Color.RED)
                        .footer(new DiscordEmbed.EmbedFooter("Requested by "+ command.getUser().getName(),"https://cdn.discordapp.com/avatars/"+command.getUser().getId()+ "/" + command.getUser().getAvatarId()+ ".png"))
                        .build();
                DiscordMessenger.sendMessage(command.getChannelId(), error);
            }
            else {
                Collection<UUID> names = Database.get().getUsersByIP(playerIP);
                if (names.isEmpty()){
                    DiscordEmbed altsDed = DiscordEmbed.builder()
                            .title(new DiscordEmbed.EmbedTitle(  playerIP + " doesnt exist on our database", null))
                            .color(Color.RED)
                            .footer(new DiscordEmbed.EmbedFooter("Requested by " + command.getUser().getName(), "https://cdn.discordapp.com/avatars/" + command.getUser().getId() + "/" + command.getUser().getAvatarId() + ".png"))
                            .build();
                    DiscordMessenger.sendMessage(command.getChannelId(), altsDed);
                }
                else {
                    int i = 0;
                    StringBuilder builder = new StringBuilder();
                    for (UUID uuid : names) {
                        String names2 = Bukkit.getServer().getOfflinePlayer(uuid).getName();
                        builder.append(++i).append(". `").append(names2).append("`\n");
                    }
                    DiscordEmbed altsTest = DiscordEmbed.builder()
                            .author(new DiscordEmbed.EmbedAuthor("Scanning alts of " + playerIP, "https://cdn.discordapp.com/attachments/836464907984568330/946464830225801226/PinClipart.com_analyze-clipart_5366105.png", null))
                            .color(Color.CYAN)
                            .description(builder.toString())
                            .footer(new DiscordEmbed.EmbedFooter("Requested by " + command.getUser().getName(), "https://cdn.discordapp.com/avatars/" + command.getUser().getId() + "/" + command.getUser().getAvatarId() + ".png"))
                            .build();
                    DiscordMessenger.sendMessage(command.getChannelId(), altsTest);
                }
            }
        }
        else {
            DiscordEmbed noPerm = DiscordEmbed.builder()
                    .title(new DiscordEmbed.EmbedTitle("Insufficient Permissions !",null))
                    .color(Color.RED)
                    .footer(new DiscordEmbed.EmbedFooter("get good!",null))
                    .build();
            DiscordMessenger.sendMessage(command.getChannelId(),noPerm);
        }
    }
}

