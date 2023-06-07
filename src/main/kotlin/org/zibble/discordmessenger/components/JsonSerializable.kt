package org.zibble.discordmessenger.components

import com.google.gson.JsonElement
import org.zibble.discordmessenger.DiscordMessenger

interface JsonSerializable {

    fun toJson() : JsonElement = DiscordMessenger.instance.gson.toJsonTree(this)

    fun toJsonString() : String = DiscordMessenger.instance.gson.toJson(this)

}