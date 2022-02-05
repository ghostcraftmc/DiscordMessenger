package com.pepedevs.discordmessenger.messagable;

import com.google.gson.JsonObject;

public interface JsonSerializable {

    JsonObject toJson();

    String toJsonString();

}
