package org.zibble.discordmessenger.components;

import com.google.gson.JsonObject;

public interface JsonSerializable {

    JsonObject toJson();

    default String toJsonString() {
        return this.toJson().toString();
    }

}
