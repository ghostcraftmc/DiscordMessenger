package org.zibble.discordmessenger.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.zibble.discordmessenger.util.PermissionUtil;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class User implements JsonSerializable {

    private final long id;
    private String discriminator;
    private String name;
    private String avatarId;
    private boolean bot, owner;
    private long effectivePermission;
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
                json.get("owner").getAsBoolean(),
                roles,
                json.get("effectivePermission").getAsLong());
    }

    public User(long id, String discriminator, String name, String avatarId, boolean bot, boolean owner, List<Role> roles, long effectivePermission) {
        this.id = id;
        this.discriminator = discriminator;
        this.name = name;
        this.avatarId = avatarId;
        this.bot = bot;
        this.owner = owner;
        this.roles = roles;
        this.effectivePermission = effectivePermission;
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

    public boolean isBot() {
        return bot;
    }

    public boolean isOwner() {
        return owner;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public long getEffectivePermission() {
        return effectivePermission;
    }

    public EnumSet<Permission> getPermissions() {
        return Permission.getPermissions(effectivePermission);
    }

    public boolean hasPermission(Permission... permissions) {
        return PermissionUtil.checkPermission(this, permissions);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("discriminator", discriminator);
        json.addProperty("name", name);
        json.addProperty("avatarId", avatarId);
        json.addProperty("bot", bot);
        json.addProperty("owner", owner);
        json.addProperty("effectivePermission", effectivePermission);
        JsonArray roles = new JsonArray();
        for (Role role : this.roles) {
            roles.add(role.toJson());
        }
        json.add("roles", roles);
        return json;
    }

}
