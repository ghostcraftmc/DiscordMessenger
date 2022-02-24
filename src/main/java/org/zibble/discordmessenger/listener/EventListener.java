package org.zibble.discordmessenger.listener;

import org.zibble.discordmessenger.DiscordMessenger;
import org.zibble.discordmessenger.components.messagable.DiscordEmbed;
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
                .author(new DiscordEmbed.EmbedAuthor("GAMEMODE CHANGE TRIGGERED", "https://cdn.discordapp.com/attachments/836464907984568330/940618393059471381/pngwing.com.png",null))
                .thumbnailUrl("https://mc-heads.net/avatar/" + username + "/256.png")
                .title(new DiscordEmbed.EmbedTitle("Survival S9",null))
                .description("`" + username + "` changed gamemode to `" + changed + "`")
                .color(Color.GREEN)
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184",alert1);
    }

}
