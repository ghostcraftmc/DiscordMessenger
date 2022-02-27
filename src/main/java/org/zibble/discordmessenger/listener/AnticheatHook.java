package org.zibble.discordmessenger.listener;

import org.bukkit.Bukkit;
import org.zibble.discordmessenger.DiscordMessenger;
import org.zibble.discordmessenger.components.messagable.DiscordEmbed;
import godseye.GodsEyeAlertEvent;
import godseye.GodsEyePunishPlayerEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.time.Instant;

public class AnticheatHook implements Listener {

    double a = Bukkit.getTPS()[0];
    String b = String.valueOf(a);

    @EventHandler
    public void violationAlert(GodsEyeAlertEvent event){
        DiscordEmbed msg = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("Violation Alert","https://cdn.discordapp.com/attachments/836464907984568330/946336139843862558/pngegg.png",null))
                .thumbnailUrl("https://minecraftitemids.com/item/64/" + event.getPlayer().getName() + ".png")
                .title(new DiscordEmbed.EmbedTitle(event.getPlayer().getName()+ " Suspected using " + event.getCheckType().getName(),null))
                .field(new DiscordEmbed.EmbedField("Violation Count","`"+event.getViolationCount()+"`",true))
                .field(new DiscordEmbed.EmbedField("Hack Type", event.getCheckType().getName(),true))
                .field(new DiscordEmbed.EmbedField("Ping","`" + event.getPlayer().getPing() + "`",true))
                .field(new DiscordEmbed.EmbedField("Server TPS", "`" + b +"`",true))
                .footer(new DiscordEmbed.EmbedFooter(event.getPlayer().getServer().getName() + "✦","https://cdn.discordapp.com/icons/844128619831361556/d58acb6aa19e7e6478048587af0a3dd4.webp?"))
                .timestamp(Instant.now())
                .color(Color.MAGENTA)
                .build();
        DiscordMessenger.sendMessage("939849504964870184",msg);
    }

    @EventHandler
    public void anticheatPunish(GodsEyePunishPlayerEvent event){
        DiscordEmbed msg = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("PUNISHMENT "+event.getPunishType().name(),"https://cdn.discordapp.com/attachments/836464907984568330/947089867643441162/pngegg_1.png",null))
                .thumbnailUrl("https://minecraftitemids.com/item/64/" + event.getPlayer().getName() + ".png")
                .title(new DiscordEmbed.EmbedTitle(event.getPlayer().getName()+ " was punished",null))
                .field(new DiscordEmbed.EmbedField("Punish Type","`"+event.getPunishType().name()+"`",true))
                .field(new DiscordEmbed.EmbedField("Hack Type", event.getCheckType().getName(),true))
                .field(new DiscordEmbed.EmbedField("Ping","`" + event.getPlayer().getPing() + "`",true))
                .field(new DiscordEmbed.EmbedField("Server TPS", "`" + b +"`",true))
                .footer(new DiscordEmbed.EmbedFooter(event.getPlayer().getServer().getName() + "✦","https://cdn.discordapp.com/icons/844128619831361556/d58acb6aa19e7e6478048587af0a3dd4.webp?"))
                .timestamp(Instant.now())
                .color(Color.decode("#9433FF"))
                .build();
        DiscordMessenger.sendMessage("939849504964870184",msg);
    }
}