package org.zibble.discordmessenger.components.messagable;

import com.google.gson.JsonObject;
import org.zibble.discordmessenger.components.JsonSerializable;

public interface Message extends JsonSerializable {

    String getType();

    @Override
    JsonObject toJson();

    @Override
    String toJsonString();

}
