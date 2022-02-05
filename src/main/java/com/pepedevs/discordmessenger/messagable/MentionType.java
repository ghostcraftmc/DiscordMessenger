package com.pepedevs.discordmessenger.messagable;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public enum MentionType implements JsonSerializable {

    USER,
    ROLE,
    CHANNEL,
    EMOTE,
    HERE,
    EVERYONE,
    ;

    public static MentionType fromJson(JsonObject json) {
        return MentionType.valueOf(json.getAsJsonPrimitive().getAsString());
    }

    @Override
    public JsonObject toJson() {
        return new JsonPrimitive(this.name()).getAsJsonObject();
    }

    @Override
    public String toJsonString() {
        return this.toJson().toString();
    }

}
