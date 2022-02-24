package org.zibble.discordmessenger.listener;

import org.zibble.discordmessenger.components.messagable.DiscordEmbed;
import godseye.GodsEyeAlertEvent;
import godseye.GodsEyePunishPlayerEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AnticheatHook implements Listener {

    @EventHandler
    public void punishEvent (GodsEyeAlertEvent event){
        DiscordEmbed msg = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("AntiCheat Violation","https://cdn.discordapp.com/attachments/836464907984568330/946336139843862558/pngegg.png",null))
                .thumbnailUrl("https://minecraftitemids.com/item/64/" + event.getPlayer().getName() + ".png")

    }
}
