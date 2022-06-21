package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.DiscordMessenger
import org.zibble.discordmessenger.components.entity.MessageChannel
import org.zibble.discordmessenger.components.entity.User
import org.zibble.discordmessenger.components.readable.DiscordMessage
import java.time.OffsetDateTime

class SlashCommand(
    private val channel: MessageChannel,
    val name: String,
    private val id: Long,
    private val type: CommandType = CommandType.SLASH,
    private val user: User,
    val subCommandName: String? = null,
    val subCommandGroup: String? = null,
    val optionMappings: OptionMapping,
    private val sentTime: OffsetDateTime,
    private val publicPermission: Long
) : Command {

    class OptionMapping(
        private val data: Map<String, Any>
    ) {

        @Suppress("UNCHECKED_CAST")
        fun <T> get(key: String, type: Class<T>): T? {
            return data[key] as? T
        }

        fun get(key: String): Any? {
            return data[key]
        }

        fun has(key: String): Boolean {
            return data.containsKey(key)
        }

    }

    override fun getId(): Long = id

    override fun getType(): CommandType = type

    override fun getChannel(): MessageChannel = channel

    override fun getUser(): User = user

    override fun getSentTime(): OffsetDateTime = sentTime

    override fun getPublicPermission(): Long = publicPermission

}