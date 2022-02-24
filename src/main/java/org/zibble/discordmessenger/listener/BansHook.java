package org.zibble.discordmessenger.listener;

import org.zibble.discordmessenger.DiscordMessenger;
import org.zibble.discordmessenger.components.messagable.DiscordEmbed;
import me.leoko.advancedban.bukkit.event.PunishmentEvent;
import me.leoko.advancedban.bukkit.event.RevokePunishmentEvent;
import me.leoko.advancedban.utils.Punishment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.time.Instant;

public class BansHook implements Listener {

    @EventHandler
    public void punishEvent(PunishmentEvent event){
        Punishment punish = event.getPunishment();
        DiscordEmbed alert = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("PUNISHMENT - " + event.getPunishment().getType(),"https://pngimg.com/uploads/exclamation_mark/exclamation_mark_PNG64.png",null))
                .thumbnailUrl("https://mc-heads.net/avatar/" + punish.getName() + "/256.png")
                .color(Color.decode("#9433FF"))
                .title(new DiscordEmbed.EmbedTitle("Survival S9",null))
                .field(new DiscordEmbed.EmbedField("Name","`" + punish.getName() + "`",true))
                .field(new DiscordEmbed.EmbedField("\u200B","\u200B",true))
                .field(new DiscordEmbed.EmbedField("Punished By","`" + punish.getOperator() + "`",true))
                .field(new DiscordEmbed.EmbedField("Amount","`" +(punish.getDuration(true)) + "`",true))
                .field(new DiscordEmbed.EmbedField("\u200B","\u200B",true))
                .field(new DiscordEmbed.EmbedField("ID","`" + String.valueOf(punish.getId()) + "`",true))
                .field(new DiscordEmbed.EmbedField("Reason","`" + punish.getReason() + "`",true))
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184",alert);


    }

    @EventHandler
    public void revokePunishment (RevokePunishmentEvent event) {
        Punishment punish = event.getPunishment();
        DiscordEmbed msg = DiscordEmbed.builder()
                .color(Color.decode("#32FB7D"))
                .author(new DiscordEmbed.EmbedAuthor("PUNISHMENT REMOVED","https://cdn.discordapp.com/attachments/836464907984568330/939115383024140338/green-hook-mark-0ef5edf305f7b7af6ea1ddd9ec7aef54.png",null))
                .thumbnailUrl("https://mc-heads.net/avatar/" + punish.getName() + "/256.png")
                .color(Color.decode("#32FB7D"))
                .title(new DiscordEmbed.EmbedTitle("Survival S9",null))
                .field(new DiscordEmbed.EmbedField("Name","`" + punish.getName() + "`",true))
                .field(new DiscordEmbed.EmbedField("ID","`" + String.valueOf(punish.getId()) + "`",true))
                .field(new DiscordEmbed.EmbedField("Was Punished By","`" + punish.getOperator() + "`",true))
                .field(new DiscordEmbed.EmbedField("Reason","`" + punish.getReason() + "`",true))
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184",msg);
    }

}
