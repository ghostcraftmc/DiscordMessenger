package org.zibble.discordmessenger.components.action

import java.util.concurrent.ThreadLocalRandom

class RegisterWebhookClientAction(
    val webhookUrl: WebhookUrl
) : Action(ThreadLocalRandom.current().nextLong()) {

    companion object {
        fun of(url: WebhookUrl): RegisterWebhookClientAction {
            return RegisterWebhookClientAction(url)
        }
    }

    override fun getKey(): String = "registerWebhook"

    override fun getName(): String = "Register Webhook Client"

}