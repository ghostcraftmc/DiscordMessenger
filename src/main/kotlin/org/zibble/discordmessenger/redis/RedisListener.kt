package org.zibble.discordmessenger.redis

import com.google.gson.JsonObject
import io.lettuce.core.pubsub.RedisPubSubAdapter
import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.commands.CommandFramework
import org.zibble.discordmessenger.components.entity.*
import org.zibble.discordmessenger.components.messagable.LegacyCommand
import org.zibble.discordmessenger.components.messagable.SlashCommand
import org.zibble.discordmessenger.components.readable.DiscordMessage

class RedisListener : RedisPubSubAdapter<String, String>() {

    override fun message(channel: String, message: String) {
        if (channel != DiscordMessenger.CHANNEL) return
        val json = DiscordMessenger.getInstance().gson.fromJson(message, JsonObject::class.java)
        if (json.has(COMMAND)) {
            val sub = json.getAsJsonObject(COMMAND)
            val command = if (sub["type"].asString.equals("SLASH", true)) {
                DiscordMessenger.getInstance().gson.fromJson(sub, SlashCommand::class.java)
            } else {
                DiscordMessenger.getInstance().gson.fromJson(sub, LegacyCommand::class.java)
            }
            Permission.PUBLIC_PERMISSION_RAW = command.getPublicPermission()
            CommandFramework.runCommand(command)
        } else if (json.has(BUTTON_INTERACTION)) {
            val interaction = DiscordMessenger.getInstance().gson.fromJson(json[BUTTON_INTERACTION], ButtonInteraction::class.java)
            interaction.handleInteraction()
        }
    }

    companion object {
        const val MESSAGE = "message"
        const val COMMAND_REPLY = "commandReply"
        const val COMMAND = "command"
        const val BUTTON_INTERACTION = "button_interaction"

        val intractableButtons = HashMap<String, (User, MessageChannel) -> Unit>()

        fun checkButtons(message: DiscordMessage) {
            message.actionRow.forEach {
                if (it.getComponents().first() is Button) {
                    it.getComponents().forEach { button -> button as Button
                        if (button.shouldSendInteraction) {
                            intractableButtons[button.custom_id ?: button.url!!] = button.interaction
                        }
                    }
                }
            }
        }
    }
}
