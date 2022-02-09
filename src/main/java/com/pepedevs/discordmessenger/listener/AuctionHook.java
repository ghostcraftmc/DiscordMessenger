package com.pepedevs.discordmessenger.listener;

import com.olziedev.playerauctions.api.events.PlayerAuctionBuyEvent;
import com.olziedev.playerauctions.api.events.PlayerAuctionSellEvent;
import com.pepedevs.discordmessenger.DiscordMessenger;
import com.pepedevs.discordmessenger.messagable.DiscordEmbed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;

public class AuctionHook implements Listener {

    @EventHandler
    public void playerAuctionBuy(PlayerAuctionBuyEvent event) {
        DiscordEmbed msg = DiscordEmbed.builder()
                .color(Color.decode("#48f542"))
                .author(new DiscordEmbed.EmbedAuthor(event.getBuyer().getName()+" bought "+ event.getItemStack().getType()+" for "+ event.getPrice(),"https://crafatar.com/avatars/"+ event.getBuyer().getUniqueId()+"/",null))
                .thumbnailUrl("https://minecraftitemids.com/item/64/" + event.getPlayerAuction().getItem().getType().toString() + ".png")
                .title(new DiscordEmbed.EmbedTitle("Auction Information:",null))
                .field(new DiscordEmbed.EmbedField("Buyer","`" + event.getBuyer().getName() + "`",true))
                .field(new DiscordEmbed.EmbedField("Seller","`" + event.getPlayerAuction().getAuctionPlayer().getName() + "`",true))
                .field(new DiscordEmbed.EmbedField("Item",event.getItemStack().getType().toString(),true))
                .field(new DiscordEmbed.EmbedField("Amount","`" + (String.valueOf(event.getItemStack().getAmount())) + "`",true))
                .field(new DiscordEmbed.EmbedField("Price",String.valueOf(event.getPrice()),true))
                .footer(new DiscordEmbed.EmbedFooter("Auction ID "+event.getPlayerAuction().getID(),null))
                .build();
        DiscordMessenger.sendMessage("939849504964870184",msg);

    }
    @EventHandler
    public void playerAuctionSell(PlayerAuctionSellEvent event) {
        DiscordEmbed msg = DiscordEmbed.builder()
                .color(Color.decode("#4287f5"))
                .author(new DiscordEmbed.EmbedAuthor("Selling "+ event.getPlayerAuction().getItem().getType()+ " for "+ event.getPlayerAuction().getPrice(), "https://crafatar.com/avatars/"+event.getSeller().getUniqueId()+"/",null))
                .thumbnailUrl("https://minecraftitemids.com/item/64/" + event.getPlayerAuction().getItem().getType().toString() + ".png")
                .title(new DiscordEmbed.EmbedTitle("Auction Information:",null))
                .field(new DiscordEmbed.EmbedField("Seller","`" + event.getSeller().getName() + "`", true))
                .field(new DiscordEmbed.EmbedField("Item",event.getPlayerAuction().getItem().getType().toString(),true))
                .field(new DiscordEmbed.EmbedField("Amount",String.valueOf(event.getPlayerAuction().getItem().getAmount()),true))
                .field(new DiscordEmbed.EmbedField("Price","`" + (String.valueOf(event.getPlayerAuction().getPrice())) + "`",true))
                .footer(new DiscordEmbed.EmbedFooter("Auction ID: "+event.getPlayerAuction().getID(),null))
                .build();
        DiscordMessenger.sendMessage("939849504964870184",msg);

    }

}
