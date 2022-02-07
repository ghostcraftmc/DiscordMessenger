package com.pepedevs.discordmessenger.listener;

import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.awt.*;
import java.time.Instant;

public class EventListener implements Listener {

    @EventHandler
    public void handleGamemodeChange(PlayerGameModeChangeEvent event){
        String username = event.getPlayer().getName();
        GameMode changed = event.getNewGameMode();
        DiscordEmbed alert1 = DiscordEmbed.builder()
                .title(new DiscordEmbed.EmbedTitle("Gamemode Change Alert",null))
                .description(username + " changed gamemode to" + changed)
                .color(Color.CYAN)
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184",alert1);
    }

}
