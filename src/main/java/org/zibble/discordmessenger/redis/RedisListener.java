package org.zibble.discordmessenger.redis;

import com.google.gson.JsonObject;
import org.zibble.discordmessenger.DiscordMessenger;
import org.zibble.discordmessenger.commands.CommandFramework;
import org.zibble.discordmessenger.components.Permission;
import org.zibble.discordmessenger.components.readable.ReceivedCommand;
import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {

    public static final String COMMAND = "command";
    public static final String PUBLIC_PERMISSION = "public-permission";

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(DiscordMessenger.CHANNEL)) return;

        JsonObject json = DiscordMessenger.getInstance().getGson().fromJson(message, JsonObject.class);
        if (json.has(COMMAND)) {
            JsonObject sub = json.getAsJsonObject(COMMAND);
            ReceivedCommand command = ReceivedCommand.fromJson(sub);
            CommandFramework.getInstance().runCommand(command);
        } else if (json.has(PUBLIC_PERMISSION)) {
            Permission.PUBLIC_PERMISSION_RAW = json.get(PUBLIC_PERMISSION).getAsLong();
        }
    }

}
