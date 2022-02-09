package com.pepedevs.discordmessenger.listener;

import com.Zrips.CMI.events.CMIPlayerUnVanishEvent;
import com.Zrips.CMI.events.CMIPlayerVanishEvent;
import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.time.Instant;

public class CMIHook implements Listener {

    @EventHandler
    public void vanishEvent(CMIPlayerVanishEvent event){
        String username = event.getPlayer().getName();
        DiscordEmbed alert = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("VANISH TRIGGERED", "https://cdn.discordapp.com/attachments/836464907984568330/940618393059471381/pngwing.com.png",null))
                .thumbnailUrl("https://mc-heads.net/avatar/" + username + "/256.png")
                .title(new DiscordEmbed.EmbedTitle("Survival S9", null))
                .description("`" + username + "` vanished")
                .color(Color.ORANGE)
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184",alert);

    }

    @EventHandler
    public void unvanishEvent(CMIPlayerUnVanishEvent event){
        String username = event.getPlayer().getName();
        DiscordEmbed alert = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("UNVANISH TRIGGERED", "https://cdn.discordapp.com/attachments/836464907984568330/940618393059471381/pngwing.com.png",null))
                .thumbnailUrl("https://mc-heads.net/avatar/" + username + "/256.png")
                .title(new DiscordEmbed.EmbedTitle("Survival S9", null))
                .description("`" + username + "` became visible")
                .color(Color.PINK)
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184",alert);

    }

}
