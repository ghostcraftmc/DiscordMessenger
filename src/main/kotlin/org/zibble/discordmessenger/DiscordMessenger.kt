package org.zibble.discordmessenger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.bukkit.plugin.java.JavaPlugin
import org.zibble.discordmessenger.components.Component
import org.zibble.discordmessenger.components.action.Action
import org.zibble.discordmessenger.components.action.reply.ActionReply
import org.zibble.discordmessenger.components.entity.Button
import org.zibble.discordmessenger.components.entity.SelectMenu
import org.zibble.discordmessenger.components.readable.ButtonReply
import org.zibble.discordmessenger.components.readable.CommandReply
import org.zibble.discordmessenger.components.readable.DiscordMessage
import org.zibble.discordmessenger.components.readable.SelectMenuReply
import org.zibble.discordmessenger.redis.RedisListener
import org.zibble.discordmessenger.redis.RedisListener.Companion.waitingReply
import org.zibble.discordmessenger.util.gson.ByteArrayAdaptor
import org.zibble.discordmessenger.util.gson.ColorAdaptor
import org.zibble.discordmessenger.util.gson.ComponentAdaptor
import org.zibble.discordmessenger.util.gson.OffsetDateTimeAdaptor
import java.awt.Color
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.time.OffsetDateTime
import java.util.concurrent.CompletableFuture

@OptIn(ExperimentalLettuceCoroutinesApi::class)
class DiscordMessenger : JavaPlugin() {

    init {
        instance = this
    }

    lateinit var redisClient: RedisClient
        private set

    lateinit var coroutineRedisConnection: RedisCoroutinesCommands<String, String>
        private set

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeAdaptor())
        .registerTypeAdapter(Color::class.java, ColorAdaptor())
        .registerTypeAdapter(ByteArray::class.java, ByteArrayAdaptor())
        .registerTypeAdapter(Component::class.java, ComponentAdaptor())
        .serializeNulls()
        .setLenient()
        .create()

    val coroutineScope = CoroutineScope(Dispatchers.IO + CoroutineName("DiscordMessenger"))

    override fun onEnable() {
        // Plugin startup logic
        val file = File(dataFolder, "config.json")
        if (!file.exists()) {
            saveResource("config.json", false)
        }

        val config = gson.fromJson(BufferedReader(FileReader(file)), Config::class.java)

        val user = if (config.username.isBlank()) {
            config.password
        } else {
            config.username + ":" + config.password
        }

        redisClient = RedisClient.create("redis://$user@${config.host}:${config.port}")
        val pubsub = redisClient.connectPubSub()
        pubsub.addListener(RedisListener(coroutineScope))
        pubsub.async().subscribe(CHANNEL)

        coroutineRedisConnection = redisClient.connect().coroutines()
    }

    override fun onDisable() {
        redisClient.shutdown()
    }

    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    companion object {
        lateinit var instance: DiscordMessenger
            private set
        
        const val CHANNEL = "discord-logger-v2"
        
        suspend fun replyCommand(commandId: Long, message: DiscordMessage, ephemeral: Boolean) {
            val reply = CommandReply(commandId, message, ephemeral)
            val json = JsonObject().apply {
                add(RedisListener.COMMAND_REPLY, reply.toJson())
            }
            instance.coroutineRedisConnection.publish(CHANNEL, json.toString())
        }

        suspend fun replyButton(button: Button, message: DiscordMessage, ephemeral: Boolean) {
            val reply = ButtonReply(button, message, ephemeral)
            val json = JsonObject().apply {
                add(RedisListener.BUTTON_REPLY, reply.toJson())
            }
            instance.coroutineRedisConnection.publish(CHANNEL, json.toString())
        }

        suspend fun replySelectMenu(menu: SelectMenu, message: DiscordMessage, ephemeral: Boolean) {
            val reply = SelectMenuReply(menu, message, ephemeral)
            val json = JsonObject().apply {
                add(RedisListener.SELECT_MENU_REPLY, reply.toJson())
            }
            instance.coroutineRedisConnection.publish(CHANNEL, json.toString())
        }

        suspend fun sendAction(action: Action): CompletableFuture<ActionReply> {
            val json = JsonObject().apply {
                add(action.getKey(), action.toJson())
            }
            instance.coroutineRedisConnection.publish(CHANNEL, json.toString())
            val future = CompletableFuture<ActionReply>()
            waitingReply.put(action.id, future)
            return future
        }
    }
}

data class Config(
    val host: String = "",
    val port: Int = 6379,
    val username: String = "",
    val password: String = ""
)
