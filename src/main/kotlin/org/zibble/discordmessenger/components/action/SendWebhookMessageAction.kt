package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.JsonSerializable
import org.zibble.discordmessenger.components.readable.WebhookMessage
import java.util.concurrent.ThreadLocalRandom

data class SendWebhookMessageAction(
    val webhookUrl: WebhookUrl,
    val message: WebhookMessage
) : Action(ThreadLocalRandom.current().nextLong()) {

    companion object {
        fun of(webhookUrl: WebhookUrl, message: WebhookMessage) : SendWebhookMessageAction = SendWebhookMessageAction(
            webhookUrl,
            message
        )
    }

    override fun getKey(): String = "sendWebhookMessage"

    override fun getName(): String = "Send Webhook Message"

}

data class WebhookUrl(
    val url: String
) : JsonSerializable {

    companion object {
        fun of(url: String) : WebhookUrl = WebhookUrl(
            url
        )

        fun of(id: Long, token: String): WebhookUrl = WebhookUrl(
            "https://discord.com/api/webhooks/$id/$token"
        )
    }

}