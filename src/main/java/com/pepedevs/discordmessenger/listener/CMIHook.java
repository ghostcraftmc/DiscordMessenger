package com.pepedevs.discordmessenger.listener;

import com.Zrips.CMI.events.CMIPlayerUnVanishEvent;
import com.Zrips.CMI.events.CMIPlayerVanishEvent;
import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;

public class CMIHook implements Listener {

    @EventHandler
    public void vanishEvent(CMIPlayerVanishEvent event){
        String username = event.getPlayer().getName();
        DiscordEmbed alert = DiscordEmbed.builder()
                .title(new DiscordEmbed.EmbedTitle("`" + username + "` vanished!" , null))
                .color(new Color(199, 0, 57 ))
                .build();
        DiscordMessenger.sendMessage("878893790415429672",alert);

    }

    @EventHandler
    public void unvanishEvent(CMIPlayerUnVanishEvent event){
        String username = event.getPlayer().getName();
        DiscordEmbed alert = DiscordEmbed.builder()
                .title(new DiscordEmbed.EmbedTitle("`" + username + "` became visible" , null))
                .color(new Color(199, 0, 57 ))
                .build();
        DiscordMessenger.sendMessage("878893790415429672",alert);

    }

}
