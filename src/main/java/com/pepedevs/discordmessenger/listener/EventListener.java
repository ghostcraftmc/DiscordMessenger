package com.pepedevs.discordmessenger.listener;

import com.Zrips.CMI.events.CMIPlayerUnVanishEvent;
import com.Zrips.CMI.events.CMIPlayerVanishEvent;
import com.olziedev.playerauctions.api.auction.Auction;
import com.olziedev.playerauctions.api.events.PlayerAuctionBuyEvent;
import com.olziedev.playerauctions.api.events.PlayerAuctionSellEvent;
import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import godseye.GodsEyePunishPlayerEvent;
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
    public void playerAuctionBuy(PlayerAuctionBuyEvent event) {
        DiscordEmbed msg = DiscordEmbed.builder()
                .color(Color.decode("#48f542"))
                .author(new DiscordEmbed.EmbedAuthor(event.getBuyer().getName()+" bought "+ event.getItemStack().getType()+" for "+ event.getPrice(),"https://crafatar.com/avatars/"+ event.getBuyer().getUniqueId()+"/",null))
                .title(new DiscordEmbed.EmbedTitle("Auction Information:",null))
                .field(new DiscordEmbed.EmbedField("Buyer",event.getBuyer().getName(),true))
                .field(new DiscordEmbed.EmbedField("Seller",event.getPlayerAuction().getAuctionPlayer().getName(),true))
                .field(new DiscordEmbed.EmbedField("Item",event.getItemStack().getType().toString(),true))
                .field(new DiscordEmbed.EmbedField("Amount",String.valueOf(event.getItemStack().getAmount()),true))
                .field(new DiscordEmbed.EmbedField("Price",String.valueOf(event.getPrice()),true))
                .footer(new DiscordEmbed.EmbedFooter("Auction ID "+event.getPlayerAuction().getID(),null))
                .build();
        DiscordMessenger.sendMessage("939154546813440000",msg);

    }
    @EventHandler
    public void playerAuctionSell(PlayerAuctionSellEvent event) {
        DiscordEmbed msg = DiscordEmbed.builder()
                .color(Color.decode("#4287f5"))
                .author(new DiscordEmbed.EmbedAuthor(event.getSeller().getName()+ " is selling "+ event.getPlayerAuction().getItem().getType()+ " for "+ event.getPlayerAuction().getPrice(), "https://crafatar.com/avatars/"+event.getSeller().getUniqueId()+"/",null))
                .title(new DiscordEmbed.EmbedTitle("Auction Information:",null))
                .field(new DiscordEmbed.EmbedField("Seller",event.getSeller().getName(), true))
                .field(new DiscordEmbed.EmbedField("Item",event.getPlayerAuction().getItem().getType().toString(),true))
                .field(new DiscordEmbed.EmbedField("Amount",String.valueOf(event.getPlayerAuction().getItem().getAmount()),true))
                .field(new DiscordEmbed.EmbedField("Price",String.valueOf(event.getPlayerAuction().getPrice()),true))
                .footer(new DiscordEmbed.EmbedFooter("Auction ID: "+event.getPlayerAuction().getID(),null))
                .build();
        DiscordMessenger.sendMessage("939154546813440000",msg);

    }


}
