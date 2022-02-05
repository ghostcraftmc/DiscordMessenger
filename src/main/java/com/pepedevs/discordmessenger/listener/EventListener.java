package com.pepedevs.discordmessenger.listener;

import com.Zrips.CMI.events.CMIPlayerUnVanishEvent;
import com.Zrips.CMI.events.CMIPlayerVanishEvent;
import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import me.leoko.advancedban.bukkit.event.PunishmentEvent;
import me.leoko.advancedban.bukkit.event.RevokePunishmentEvent;
import me.leoko.advancedban.utils.Punishment;
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
        DiscordMessenger.sendMessage("878893790415429672",alert1);
    }

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

    @EventHandler
    public void punishEvent(PunishmentEvent event){
        Punishment punish = event.getPunishment();
        DiscordEmbed alert = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("PUNISHMENT - " + event.getPunishment().getType(),"https://pngimg.com/uploads/exclamation_mark/exclamation_mark_PNG64.png",null))
                .color(Color.decode("#9433FF"))
                .title(new DiscordEmbed.EmbedTitle("Survival S9",null))
                .field(new DiscordEmbed.EmbedField("Name",punish.getName(),true))
                .field(new DiscordEmbed.EmbedField("ID",String.valueOf(punish.getId()),true))
                .field(new DiscordEmbed.EmbedField("Punished By",punish.getOperator(),true))
                .field(new DiscordEmbed.EmbedField("Amount",punish.getDuration(true),true))
                .field(new DiscordEmbed.EmbedField("Type",punish.getType().getName(),true))
                .field(new DiscordEmbed.EmbedField("Reason",punish.getReason(),true))
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939091234872508436",alert);


    }

    @EventHandler
    public void revokePunishment (RevokePunishmentEvent event) {
        Punishment punish = event.getPunishment();
        DiscordEmbed msg = DiscordEmbed.builder()
                .color(Color.decode("#32FB7D"))
                .author(new DiscordEmbed.EmbedAuthor("PUNISHMENT REMOVED","https://cdn.discordapp.com/attachments/836464907984568330/939115383024140338/green-hook-mark-0ef5edf305f7b7af6ea1ddd9ec7aef54.png",null))
                .title(new DiscordEmbed.EmbedTitle( "Survival S9",null))
                .field(new DiscordEmbed.EmbedField("Name",punish.getName(),true))
                .field(new DiscordEmbed.EmbedField("ID",String.valueOf(punish.getId()),true))
                .field(new DiscordEmbed.EmbedField("Punished By",punish.getOperator(),true))
                .field(new DiscordEmbed.EmbedField("Type",punish.getType().getName(),true))
                .field(new DiscordEmbed.EmbedField("Reason",punish.getReason(),true))
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939091234872508436",msg);
    }

}
