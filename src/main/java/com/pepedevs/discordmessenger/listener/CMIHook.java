package com.pepedevs.discordmessenger.listener;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.events.CMIPlayerTeleportEvent;
import com.Zrips.CMI.events.CMIPlayerUnVanishEvent;
import com.Zrips.CMI.events.CMIPlayerVanishEvent;
import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.BoundingBox;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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

    @EventHandler
    public void tpEvent(CMIPlayerTeleportEvent event){
        String username = event.getPlayer().getName();
        int radius = 2;
        Player player = event.getPlayer();
        Boolean YES = CMI.getInstance().getPlayerManager().getUser(event.getPlayer().getName()).isVanished();
        BoundingBox box = BoundingBox.of(event.getSafe().getSafeLoc().clone().subtract(radius, radius, radius), event.getSafe().getSafeLoc().clone().add(radius, radius, radius));
        List<String> players = event.getPlayer().getWorld().getNearbyEntities(box , entity -> entity instanceof Player).stream().map(entity -> ((Player) entity).getName()).collect(Collectors.toList());
        String name = players.size() > 0 ? players.get(0) : "Noone";
        if (YES){
            DiscordEmbed tp = DiscordEmbed.builder()
                    .author(new DiscordEmbed.EmbedAuthor("Vanish TP triggered","https://cdn.discordapp.com/attachments/836464907984568330/944991273822076980/kindpng_165907_1.png",null))
                    .thumbnailUrl("https://mc-heads.net/avatar/" + username + "/256.png")
                    .title(new DiscordEmbed.EmbedTitle("Survival S9", null))
                    .description("`" + username + "` Teleported to someone while in vanish")
                    .field(new DiscordEmbed.EmbedField("Players Nearby","`"+name+"`",true))
                    .color(Color.CYAN)
                    .build();
            DiscordMessenger.sendMessage("939849504964870184",tp);
        }
    }
}


