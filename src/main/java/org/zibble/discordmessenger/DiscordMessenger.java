package org.zibble.discordmessenger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.zibble.discordmessenger.commands.CommandFramework;
import org.zibble.discordmessenger.components.messagable.Message;
import org.zibble.discordmessenger.listener.EventListener;
import org.zibble.discordmessenger.redis.RedisListener;

import java.io.File;

public final class DiscordMessenger extends JavaPlugin {

    private static DiscordMessenger instance;
    public static final String CHANNEL = "discord-logger";
    public static final String PREFIX = ChatColor.AQUA + "[Alerts]";

    private RedisClient redisClient;
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

        this.registerCommand();

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.setupHook();

        this.redisClient = RedisClient.create("redis://" + config.getPassword() + "@" + config.getHost() + ":" + config.getPort());
        StatefulRedisPubSubConnection<String, String> pubsub = this.redisClient.connectPubSub();
        pubsub.addListener(new RedisListener());
        pubsub.async().subscribe(CHANNEL);
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public Gson getGson() {
        return gson;
    }

    public CommandFramework getCommandFramework() {
        return commandFramework;
    }

    private void setupHook(){

    }

    private void registerCommand(){
    }


    public static void sendMessage(String channelId, Message message) {
        JsonObject json = new JsonObject();
        json.addProperty("channel", channelId);
        json.add("object", message.toJson());
        JsonObject obj = new JsonObject();
        obj.add(message.getType(), json);
        instance.redisClient.connect().async().publish(CHANNEL, obj.toString());
    }

    public static DiscordMessenger getInstance() {
        return instance;
    }

}
