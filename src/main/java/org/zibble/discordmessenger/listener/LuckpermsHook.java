package org.zibble.discordmessenger.listener;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.log.LogBroadcastEvent;
import org.bukkit.event.Listener;
import org.zibble.discordmessenger.DiscordMessenger;
import org.zibble.discordmessenger.components.messagable.DiscordEmbed;

import java.time.Instant;

public class LuckpermsHook {
    private final DiscordMessenger plugin;
    private final LuckPerms luckPerms;

    public LuckpermsHook(DiscordMessenger plugin, LuckPerms luckPerms){
        this.plugin = plugin;
        this.luckPerms = luckPerms;

        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, LogBroadcastEvent.class, this::onLogBroadcast);
    }
    private void onLogBroadcast(LogBroadcastEvent event){

        String  desc = event.getEntry().getDescription();
        String source = event.getEntry().getSource().getName();
        String target = event.getEntry().getTarget().getName();
        String type = event.getEntry().getTarget().getType().toString();

        DiscordEmbed msg = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("LuckPerm Logs - Survival","https://cdn.discordapp.com/attachments/836464907984568330/947570249300054046/pngfind.com-green-arrow-png-1001642.png",null))
                .description("```ansi\n\u001b[0;37m▸ \u001b[1;33m"+source+"\u001b[0;32m [" + type +"]\u001b[1;36m " + target + " \n\u001b[0;37m▸ " + desc + "```")
                .footer(new DiscordEmbed.EmbedFooter("From "+event.getOrigin().name(),"https://cdn.discordapp.com/attachments/836464907984568330/939115383024140338/green-hook-mark-0ef5edf305f7b7af6ea1ddd9ec7aef54.png"))
                .timestamp(Instant.now())
                .thumbnailUrl("https://luckperms.net/logo.png")
                .build();
        DiscordMessenger.sendMessage("939849504964870184", msg);
    }
}
