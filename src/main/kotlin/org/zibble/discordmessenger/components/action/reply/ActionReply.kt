package org.zibble.discordmessenger.components.action.reply

import com.google.gson.JsonObject
import org.zibble.discordmessenger.components.JsonSerializable

class ActionReply(
    val actionId: Long,
    val content: JsonObject
) : JsonSerializable {
}