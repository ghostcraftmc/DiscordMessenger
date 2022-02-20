package com.pepedevs.discordmessenger.listener;

import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import me.NoChance.PvPManager.Events.PlayerCombatLogEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Instant;

public class CombatlogIntegration implements Listener {
    @EventHandler
    public void combatLog(PlayerCombatLogEvent event){
        DiscordEmbed alert = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("PVP Logger","https://crafatar.com/avatars/"+ event.getPlayer().getName()+"/",null))
                .thumbnailUrl("https://cdn.discordapp.com/attachments/836464907984568330/944998688684662824/lego-minecraft-diamond-sword-video-game-swords-7d839af1321f2a495dd1a86252ebd4f2.png")
                .title(new DiscordEmbed.EmbedTitle(event.getPlayer().getName() + " Combat Logged",null))
                .field(new DiscordEmbed.EmbedField("Player","`" + event.getPlayer().getName()+"`",true))
                .field(new DiscordEmbed.EmbedField("World","`"+event.getPlayer().getWorld().getName()+"`",true))
                .timestamp(Instant.now())
                .build();
    }
}
