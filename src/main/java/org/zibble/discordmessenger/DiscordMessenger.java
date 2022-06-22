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
import org.zibble.discordmessenger.components.Component;
import org.zibble.discordmessenger.components.action.Action;
import org.zibble.discordmessenger.components.readable.CommandReply;
import org.zibble.discordmessenger.components.readable.DiscordMessage;
import org.zibble.discordmessenger.redis.RedisListener;
import org.zibble.discordmessenger.util.gson.ByteArrayAdaptor;
import org.zibble.discordmessenger.util.gson.ColorAdaptor;
import org.zibble.discordmessenger.util.gson.ComponentAdaptor;
import org.zibble.discordmessenger.util.gson.OffsetDateTimeAdaptor;

import java.awt.*;
import java.io.File;
import java.time.OffsetDateTime;

public final class DiscordMessenger extends JavaPlugin {

    private static DiscordMessenger instance;
    public static final String CHANNEL = "discord-logger";
    public static final String PREFIX = ChatColor.AQUA + "[Alerts]";

    private RedisClient redisClient;
    private Gson gson;

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

        this.gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdaptor())
                .registerTypeAdapter(Color.class, new ColorAdaptor())
                .registerTypeAdapter(byte[].class, new ByteArrayAdaptor())
                .registerTypeAdapter(Component.class, new ComponentAdaptor())
                .serializeNulls()
                .create();

        this.registerCommand();

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

    private void setupHook(){

    }

    private void registerCommand(){
    }

    public static void replyCommand(long commandId, DiscordMessage message) {
        RedisListener.Companion.checkButtons(message);
        CommandReply reply = new CommandReply(commandId, message);
        JsonObject json = new JsonObject();
        json.add(RedisListener.COMMAND_REPLY, reply.toJson());
        instance.redisClient.connect().async().publish(CHANNEL, json.toString());
    }

    public static void sendAction(Action action) {
        JsonObject json = new JsonObject();
        json.add(action.getKey(), action.toJson());
        instance.redisClient.connect().async().publish(CHANNEL, json.toString());
    }

    public static DiscordMessenger getInstance() {
        return instance;
    }

}
