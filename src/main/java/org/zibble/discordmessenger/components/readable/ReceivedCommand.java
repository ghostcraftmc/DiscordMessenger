package org.zibble.discordmessenger.components.readable;

import com.google.gson.JsonObject;
import org.zibble.discordmessenger.components.JsonSerializable;
import org.zibble.discordmessenger.components.User;

import java.time.OffsetDateTime;

public class ReceivedCommand implements JsonSerializable {

    private final String content;
    private final String nonce;
    private String channelId;
    private boolean fromWebhook;
    private boolean mentionsEveryone;
    private OffsetDateTime sentTime;
    private User user;

    public static ReceivedCommand fromJson(JsonObject json) {
        ReceivedCommand command = new ReceivedCommand(json.get("content").getAsString(), json.get("nonce").getAsString());
        command.channelId = json.get("channelId").getAsString();
        command.fromWebhook = json.get("fromWebhook").getAsBoolean();
        command.mentionsEveryone = json.get("mentionsEveryone").getAsBoolean();
        command.sentTime = OffsetDateTime.parse(json.get("sentTime").getAsString());
        command.user = User.fromJson(json.get("user").getAsJsonObject());
        return command;
    }

    public ReceivedCommand(String content, String nonce) {
        this.content = content;
        this.nonce = nonce;
    }

    public String getContent() {
        return content;
    }

    public String getNonce() {
        return nonce;
    }

    public String getChannelId() {
        return channelId;
    }

    public boolean isFromWebhook() {
        return fromWebhook;
    }

    public boolean isMentionsEveryone() {
        return mentionsEveryone;
    }

    public OffsetDateTime getSentTime() {
        return sentTime;
    }

    public User getUser() {
        return user;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("content", content);
        json.addProperty("nonce", nonce);
        json.addProperty("channelId", channelId);
        json.addProperty("fromWebhook", fromWebhook);
        json.addProperty("mentionsEveryone", mentionsEveryone);
        json.addProperty("sentTime", sentTime.toString());
        json.add("user", user.toJson());
        return json;
    }

}
