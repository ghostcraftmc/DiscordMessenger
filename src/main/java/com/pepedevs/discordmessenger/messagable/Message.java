package com.pepedevs.discordmessenger.messagable;

import com.google.gson.JsonObject;

public interface Message extends JsonSerializable {

    String getType();

    @Override
    JsonObject toJson();

    @Override
    String toJsonString();

}
