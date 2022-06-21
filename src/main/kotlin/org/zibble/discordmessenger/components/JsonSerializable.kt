package org.zibble.discordmessenger.components

import com.google.gson.JsonElement
import org.zibble.discordmessenger.DiscordMessenger

interface JsonSerializable {

    fun toJson() : JsonElement = DiscordMessenger.getInstance().gson.toJsonTree(this)

    fun toJsonString() : String = DiscordMessenger.getInstance().gson.toJson(this)

}