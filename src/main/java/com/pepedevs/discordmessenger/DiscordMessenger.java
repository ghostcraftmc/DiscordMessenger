package com.pepedevs.discordmessenger;

import com.Zrips.CMI.CMI;
import com.google.gson.JsonObject;
import com.pepedevs.discordmessenger.listener.*;
import com.pepedevs.discordmessenger.messagable.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;

public final class DiscordMessenger extends JavaPlugin {

    private static DiscordMessenger instance;
    public static final String CHANNEL = "discord-logger";

    private JedisPool jedisPool;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.saveResource("config.yml", false);
        }

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        Config config = new Config();
        config.load(configuration);

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.setupHook();

        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxTotal(10);
        this.jedisPool = new JedisPool(jedisConfig, config.getHost(), config.getPort(), 0, config.getPassword(), false);
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    private void setupHook(){
        if (Bukkit.getPluginManager().getPlugin("CMI") != null){
            this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Hooked into CMI");
            Bukkit.getPluginManager().registerEvents(new CMIHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("AdvancedBans") != null){
            Bukkit.getPluginManager().registerEvents(new BansHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("GodsEye") != null){
            Bukkit.getPluginManager().registerEvents(new AnticheatHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("PlayerAuctions") != null){
            Bukkit.getPluginManager().registerEvents(new AuctionHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("BanItem") != null){
            Bukkit.getPluginManager().registerEvents(new ItemBanHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("Excellentcrates") != null){
            Bukkit.getPluginManager().registerEvents(new CratesHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("Luckperms") != null){
            Bukkit.getPluginManager().registerEvents(new LuckpermsHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("Votingplugin") != null){
            Bukkit.getPluginManager().registerEvents(new VotingHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("PvPManager") != null){
            Bukkit.getPluginManager().registerEvents(new CombatlogIntegration(),this);
        }
    }

    public static void sendMessage(String channelId, Message message) {
        instance.getServer().getScheduler().runTaskAsynchronously(instance, () -> {
            try (Jedis jedis = instance.getJedisPool().getResource()) {
                JsonObject json = new JsonObject();
                json.addProperty("channel", channelId);
                json.add("object", message.toJson());
                JsonObject obj = new JsonObject();
                obj.add(message.getType(), json);
                jedis.publish(CHANNEL, obj.toString());
            }
        });
    }

    public static DiscordMessenger getInstance() {
        return instance;
    }

}
