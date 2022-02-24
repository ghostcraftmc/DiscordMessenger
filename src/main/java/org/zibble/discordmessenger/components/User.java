package org.zibble.discordmessenger.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class User implements JsonSerializable {

    private final long id;
    private String discriminator;
    private String name;
    private String avatarId;
    private boolean bot;
    private List<Role> roles;

    public static User fromJson(JsonObject json) {
        List<Role> roles = new ArrayList<>();
        JsonArray rolesJson = json.getAsJsonArray("roles");
        for (JsonElement element : rolesJson) {
            roles.add(Role.fromJson(element.getAsJsonObject()));
        }
        return new User(json.get("id").getAsLong(),
                json.get("discriminator").getAsString(),
                json.get("name").getAsString(),
                json.get("avatarId").getAsString(),
                json.get("bot").getAsBoolean(),
                roles);
    }

    public User(long id, String discriminator, String name, String avatarId, boolean bot, List<Role> roles) {
        this.id = id;
        this.discriminator = discriminator;
        this.name = name;
        this.avatarId = avatarId;
        this.bot = bot;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getName() {
        return name;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("discriminator", discriminator);
        json.addProperty("name", name);
        json.addProperty("avatarId", avatarId);
        json.addProperty("bot", bot);
        JsonArray roles = new JsonArray();
        for (Role role : this.roles) {
            roles.add(role.toJson());
        }
        json.add("roles", roles);
        return json;
    }

}
