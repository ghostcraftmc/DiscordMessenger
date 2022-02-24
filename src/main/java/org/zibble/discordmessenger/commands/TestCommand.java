package org.zibble.discordmessenger.commands;

import litebans.api.Database;
import org.bukkit.Bukkit;
import org.zibble.discordmessenger.DiscordMessenger;
import org.zibble.discordmessenger.components.Permission;
import org.zibble.discordmessenger.components.messagable.DiscordEmbed;
import org.zibble.discordmessenger.components.readable.ReceivedCommand;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TestCommand implements DiscordCommand {


    @Override
    public String getCommand() {
        return "-alts";
    }


    @Override
    public void execute(ReceivedCommand command) {
        if (command.getUser() != null && command.getUser().hasPermission(Permission.ADMINISTRATOR)) {
            String playerName = command.getContent().replace(this.getCommand(), "").trim();
            if (playerName.isEmpty()) {
                DiscordEmbed error = DiscordEmbed.builder()
                        .title(new DiscordEmbed.EmbedTitle("Invalid usage!", null))
                        .color(Color.RED)
                        .build();
                DiscordMessenger.sendMessage(command.getChannelId(), error);
            }
            String query = "SELECT ip FROM {history} WHERE name=?";
            try (PreparedStatement st = Database.get().prepareStatement(query)) {
                st.setString(1, playerName);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        String ip = rs.getString("ip");
                        Collection<UUID> names = Database.get().getUsersByIP(ip);
                        List<DiscordEmbed.EmbedField> names1 = new ArrayList<>();
                        for (UUID uuid : names) {
                            String names2 = Bukkit.getServer().getOfflinePlayer(uuid).getName();
                            names1.add(new DiscordEmbed.EmbedField("Alts",names2,true));
                        }
                        DiscordEmbed altsTest = DiscordEmbed.builder()
                                .title(new DiscordEmbed.EmbedTitle("Alts of " + playerName ,null))
                                .field(names1)
                                .build();
                        DiscordMessenger.sendMessage(command.getChannelId(),altsTest);
                        // ...
                    } else {

                        DiscordEmbed altsDed = DiscordEmbed.builder()
                                .title(new DiscordEmbed.EmbedTitle(playerName + " has not yet joined our server!" ,null))
                                .build();
                        DiscordMessenger.sendMessage(command.getChannelId(),altsDed);
                        // Player not found in database
                        // ...
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}