package org.zibble.discordmessenger.components.messagable;

import com.google.gson.JsonObject;
import org.zibble.discordmessenger.components.JsonSerializable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class MessageAttachment implements JsonSerializable {

    private final String name;
    private byte[] data;

    public static MessageAttachment fromJson(JsonObject json) {
        return new MessageAttachment(
                json.get("name").getAsString(),
                Base64.getDecoder().decode(json.get("data").getAsString().getBytes()));
    }

    public MessageAttachment(@NotNull String name, @NotNull byte[] data) {
        this.name = name;
        this.data = data;
    }

    public MessageAttachment(@NotNull String name, @NotNull InputStream stream) throws IOException {
        this.name = name;

        try {
//            this.data = IOUtil.readAllBytes(stream);
        } finally {
            stream.close();
        }

    }

    public MessageAttachment(@NotNull String name, @NotNull File file) throws IOException {
        this(name, new FileInputStream(file));
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public byte[] getData() {
        return this.data;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("data", new String(Base64.getEncoder().encode(this.data)));
        return json;
    }

    @Override
    public String toJsonString() {
        return this.toJson().toString();
    }

}
