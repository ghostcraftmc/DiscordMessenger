package org.zibble.discordmessenger.components.action.sendable

import org.zibble.discordmessenger.components.action.SendableAction
import org.zibble.discordmessenger.components.readable.WebhookMessage

data class SendWebhookMessageAction(
    val webhookId: Int,
    val message: WebhookMessage
) : SendableAction("sendWebhookMessage", "Send Webhook Message") {

    companion object {
        fun of(webhookId: Int, message: WebhookMessage) : SendWebhookMessageAction = SendWebhookMessageAction(
            webhookId,
            message
        )
    }

}