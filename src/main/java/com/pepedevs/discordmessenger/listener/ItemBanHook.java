package com.pepedevs.discordmessenger.listener;

import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import fr.andross.banitem.events.DeleteBannedItemEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Instant;

public class ItemBanHook implements Listener {

    @EventHandler
    public void itemBan (DeleteBannedItemEvent event){
        String username = event.getPlayer().getName();
        DiscordEmbed ban = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("BANNED ITEM DETECTED","https://cdn.discordapp.com/attachments/836464907984568330/941049724386021436/PikPng.com_metal-gear-solid-exclamation_2738816.png",null))
                .title(new DiscordEmbed.EmbedTitle("SURVIVAL S9",null))
                .thumbnailUrl("https://mc-heads.net/avatar/" + username + "/256.png")
                .description("`" + event.getBannedItem().getType() + "` was found with `" + event.getPlayer().getName() + "`")
                .field(new DiscordEmbed.EmbedField("Player-IP","`" + PlaceholderAPI.setPlaceholders(event.getPlayer(),"%player_ip%") + "`",true))
                .field(new DiscordEmbed.EmbedField("Mode",event.getPlayer().getGameMode().name(),true))
                .field(new DiscordEmbed.EmbedField("Location","`X` : " + event.getPlayer().getLocation().getBlockX() +"\n`Y` : " + String.valueOf(event.getPlayer().getLocation().getBlockY())+"\n`Z `: " + String.valueOf(event.getPlayer().getLocation().getBlockZ())+"\n`World` : **" + String.valueOf(event.getPlayer().getLocation().getWorld().getName())+"**",true))
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184", ban);

    }

}
