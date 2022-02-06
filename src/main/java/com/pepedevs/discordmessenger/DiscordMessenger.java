package com.pepedevs.discordmessenger;

import com.Zrips.CMI.CMI;
import com.google.gson.JsonObject;
import com.pepedevs.discordmessenger.listener.AnticheatHook;
import com.pepedevs.discordmessenger.listener.BansHook;
import com.pepedevs.discordmessenger.listener.CMIHook;
import com.pepedevs.discordmessenger.listener.EventListener;
import com.pepedevs.discordmessenger.messagable.Message;
import org.bukkit.Bukkit;
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
            Bukkit.getPluginManager().registerEvents(new CMIHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("AdvancedBans") != null){
            Bukkit.getPluginManager().registerEvents(new BansHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("GodsEye") != null){
            Bukkit.getPluginManager().registerEvents(new AnticheatHook(),this);
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
