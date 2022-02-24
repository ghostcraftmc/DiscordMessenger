package org.zibble.discordmessenger.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.zibble.discordmessenger.DiscordMessenger;
import org.zibble.discordmessenger.components.messagable.DiscordEmbed;
import su.nightexpress.excellentcrates.api.crate.ICrateReward;
import su.nightexpress.excellentcrates.api.event.CrateOpenEvent;

import java.awt.*;
import java.time.Instant;

public class CratesHook implements Listener {
    @EventHandler
    public void cratesOpen(CrateOpenEvent event){

        DiscordEmbed cratelog = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor(event.getCrate().getName() + " crate open logged!","https://crafatar.com/avatars/"+ event.getPlayer().getName()+"/",null))
                .title(new DiscordEmbed.EmbedTitle("Survival S9",null))
                .field(new DiscordEmbed.EmbedField("Player","`"+event.getPlayer().getName()+"`",true))
                .field(new DiscordEmbed.EmbedField("Reward","`"+ICrateReward.PLACEHOLDER_NAME+"`",true))
                .field(new DiscordEmbed.EmbedField("Crate ID","`"+event.getCrate().getId()+"`",true))
                .thumbnailUrl("https://cdn.discordapp.com/attachments/836464907984568330/945243161469550623/Wooden-treasure-chest-with-gold-coins-on-transparent-background-PNG.png")
                .color(Color.YELLOW)
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184",cratelog);
        //ICrateReward.PLACEHOLDER_NAME
    }
}

