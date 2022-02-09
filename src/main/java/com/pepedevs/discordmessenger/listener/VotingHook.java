package com.pepedevs.discordmessenger.listener;

import com.bencodez.votingplugin.events.PlayerVoteEvent;
import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Instant;

public class VotingHook implements Listener {
    @EventHandler
    public void votingLogs(PlayerVoteEvent event) {
        DiscordEmbed msg = DiscordEmbed.builder()
                .author(new DiscordEmbed.EmbedAuthor("New votes registered on the server !", "https://cdn.discordapp.com/attachments/836464907984568330/896748030726905886/pngegg_1.png", null))
                .thumbnailUrl("https://mc-heads.net/avatar/" + event.getPlayer() + "/256.png")
                .description("**" + event.getPlayer() + "**'s vote was successfully registered on `" + event.getServiceSite() + "`")
                .field(new DiscordEmbed.EmbedField("Server", "`Survival`", true))
                .timestamp(Instant.now())
                .build();
        DiscordMessenger.sendMessage("939849504964870184", msg);

    }
}
