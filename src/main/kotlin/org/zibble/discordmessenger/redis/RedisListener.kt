package org.zibble.discordmessenger.redis

import com.google.gson.JsonObject
import io.lettuce.core.pubsub.RedisPubSubAdapter
import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.interaction.buttons.ButtonFramework
import org.zibble.discordmessenger.interaction.commands.CommandFramework
import org.zibble.discordmessenger.components.action.reply.ActionReply
import org.zibble.discordmessenger.components.entity.Permission
import org.zibble.discordmessenger.components.messagable.ButtonInteraction
import org.zibble.discordmessenger.components.messagable.LegacyCommand
import org.zibble.discordmessenger.components.messagable.SelectMenuInteraction
import org.zibble.discordmessenger.components.messagable.SlashCommand
import org.zibble.discordmessenger.interaction.selectmenu.SelectMenuFramework
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

class RedisListener : RedisPubSubAdapter<String, String>() {

    companion object {
        const val COMMAND_REPLY = "commandReply"
        const val COMMAND = "command"
        const val ACTION_REPLY = "actionReply"
        const val BUTTON_INTERACTION = "buttonInteraction"
        const val BUTTON_REPLY = "buttonReply"
        const val SELECT_MENU_INTERACTION = "selectMenuInteraction"
        const val SELECT_MENU_REPLY = "selectMenuReply"

        val waitingReply = ConcurrentHashMap<Long, CompletableFuture<ActionReply>>()
    }

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
        } else if (json.has(ACTION_REPLY)) {
            val reply = DiscordMessenger.getInstance().gson.fromJson(json[ACTION_REPLY], ActionReply::class.java)
            waitingReply.remove(reply.actionId)?.complete(reply)
        } else if (json.has(BUTTON_INTERACTION)) {
            val interaction = DiscordMessenger.getInstance().gson.fromJson(json[BUTTON_INTERACTION], ButtonInteraction::class.java)
            ButtonFramework.runAction(interaction)
        } else if (json.has(SELECT_MENU_INTERACTION)) {
            val interaction = DiscordMessenger.getInstance().gson.fromJson(json[SELECT_MENU_INTERACTION], SelectMenuInteraction::class.java)
            SelectMenuFramework.runAction(interaction)
        }
    }
}
