package org.zibble.discordmessenger.components.entity

import org.zibble.discordmessenger.components.JsonSerializable

class MessageChannel(
    val topic: String? = null,
    val parentCategoryId: Long,
    val position: Int,
    val latestMessageId: Long,
    val slowmode: Int = 0,
    val nsfw: Boolean = false
) : JsonSerializable {

}