package org.zibble.discordmessenger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.zibble.discordmessenger.commands.CommandFramework;
import org.zibble.discordmessenger.components.messagable.Message;
import org.zibble.discordmessenger.listener.*;
import org.zibble.discordmessenger.redis.RedisListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.util.concurrent.Executors;

public final class DiscordMessenger extends JavaPlugin {

    private static DiscordMessenger instance;
    public static final String CHANNEL = "discord-logger";
    public static final String PREFIX = ChatColor.AQUA + "[Alerts]";

    private JedisPool jedisPool;
    private Gson gson;
    private CommandFramework commandFramework;

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

        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.commandFramework = new CommandFramework();

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.setupHook();

        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxTotal(10);
        this.jedisPool = new JedisPool(jedisConfig, config.getHost(), config.getPort(), 0, config.getPassword(), false);

        Executors.newSingleThreadExecutor().submit(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.subscribe(new RedisListener(), CHANNEL);
            }
        });
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public Gson getGson() {
        return gson;
    }

    public CommandFramework getCommandFramework() {
        return commandFramework;
    }

    private void setupHook(){
        if (Bukkit.getPluginManager().getPlugin("CMI") != null){
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.YELLOW + " CMI detected.");
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + " Hooking into CMI");
            Bukkit.getPluginManager().registerEvents(new CMIHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("AdvancedBan") != null){
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + " AdvancedBans detected.");
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + " Hooking into AdvanceBans");
            Bukkit.getPluginManager().registerEvents(new BansHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("GodsEye") != null){
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.AQUA + "GodsEye detected");
            if (Bukkit.getPluginManager().isPluginEnabled("GodsEye")){
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.YELLOW + "Hooking into GodsEye");
                Bukkit.getPluginManager().registerEvents(new AnticheatHook(),this);
            }
            else
                this.getServer().getConsoleSender().sendMessage(PREFIX+ ChatColor.RED + "Failed to hook into GodsEye");
        }
        if (Bukkit.getPluginManager().getPlugin("PlayerAuctions") != null){
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + " Auctions detected.");
            if (Bukkit.getPluginManager().isPluginEnabled("PlayerAuctions")){
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + " Hooking into PAuctions");
                Bukkit.getPluginManager().registerEvents(new AuctionHook(),this);
            }
            else
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + " Failed to Hook into Auctions");
        }
        if (Bukkit.getPluginManager().getPlugin("BanItem") != null){
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.YELLOW + " Found BanItem");
            if (Bukkit.getPluginManager().isPluginEnabled("BanItem")){
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + " Hooking into BanItem");
                Bukkit.getPluginManager().registerEvents(new ItemBanHook(),this);
            }
            else
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + "Failed to Hook into BanItem");
        }
        if (Bukkit.getPluginManager().getPlugin("ExcellentCrates") != null){
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.GOLD + "Detected ExcellentCrates");
            if (Bukkit.getPluginManager().isPluginEnabled("ExcellentCrates")){
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.LIGHT_PURPLE + "Hooked into ExcellentCrates");
                Bukkit.getPluginManager().registerEvents(new CratesHook(),this);
            }
            else
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + "Failed to hook into ExcellentCrates :(");
        }
        if (Bukkit.getPluginManager().getPlugin("Luckperms") != null){
            Bukkit.getPluginManager().registerEvents(new LuckpermsHook(),this);
        }
        if (Bukkit.getPluginManager().getPlugin("VotingPlugin") != null){
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.GREEN + " Voting Plugin detected.");
            if (Bukkit.getPluginManager().isPluginEnabled("VotingPlugin")){
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + " Hooked into Voting plugin");
                Bukkit.getPluginManager().registerEvents(new VotingHook(),this);
            }
            else
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + " Failed to hook into Voting Plugin");
        }
        if (Bukkit.getPluginManager().getPlugin("PvPManager") != null){
            this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.YELLOW + " PvPManager Detected");
            if (Bukkit.getPluginManager().isPluginEnabled("PvPManager")){
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.GOLD + " Hooked into PvP Manager");
                Bukkit.getPluginManager().registerEvents(new CombatlogIntegration(),this);
            }
            else
                this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + " Failed to hook into PvPManager");
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
