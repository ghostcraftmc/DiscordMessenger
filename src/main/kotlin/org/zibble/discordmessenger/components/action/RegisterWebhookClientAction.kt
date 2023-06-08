package org.zibble.discordmessenger.components.action

class RegisterWebhookClientAction(
    id: Long,
    val webhookUrl: WebhookUrl
) : Action(id) {

    override fun getKey(): String = "registerWebhook"

    override fun getName(): String = "Register Webhook Client"

}