package org.zibble.discordmessenger.components;

import com.google.gson.JsonObject;

import java.awt.*;

public class Role implements JsonSerializable {

    private final long id;
    private final String name;
    private long rawPermissions;
    private int color;
    private int rawPosition;

    public static Role fromJson(JsonObject json) {
        Role role = new Role(json.get("id").getAsLong(), json.get("name").getAsString());
        role.rawPermissions = json.get("rawPermissions").getAsLong();
        role.color = json.get("color").getAsInt();
        role.rawPosition = json.get("rawPosition").getAsInt();
        return role;
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getRawPermissions() {
        return rawPermissions;
    }

    public int getColorRaw() {
        return color;
    }

    public Color getColor() {
        return new Color(color);
    }

    public int getRawPosition() {
        return rawPosition;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("name", name);
        json.addProperty("rawPermissions", rawPermissions);
        json.addProperty("color", color);
        json.addProperty("rawPosition", rawPosition);
        return json;
    }

}
