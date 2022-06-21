package org.zibble.discordmessenger.components.messagable

import org.zibble.discordmessenger.components.entity.MessageChannel
import org.zibble.discordmessenger.components.entity.User
import java.time.OffsetDateTime

class LegacyCommand(
    private val id: Long,
    val content: String,
    private val channel: MessageChannel,
    private val user: User,
    val isFromWebhook: Boolean,
    val isMentionsEveryone: Boolean,
    private val sentTime: OffsetDateTime,
    private val publicPermission: Long
) : Command {

    override fun getId(): Long = id

    override fun getType(): CommandType = CommandType.MESSAGE

    override fun getChannel(): MessageChannel = channel

    override fun getUser(): User = user

    override fun getSentTime(): OffsetDateTime = sentTime

    override fun getPublicPermission(): Long = publicPermission

}