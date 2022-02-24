package org.zibble.discordmessenger.components.messagable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.zibble.discordmessenger.components.BuildableComponent;
import org.zibble.discordmessenger.components.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.*;

public class DiscordEmbed implements Message, BuildableComponent<DiscordEmbed.Builder> {

    public static final int MAX_FIELDS = 25;

    private final OffsetDateTime timestamp;
    private final Color color;
    private final String description;
    private final String thumbnailUrl;
    private final String imageUrl;
    private final EmbedFooter footer;
    private final EmbedTitle title;
    private final EmbedAuthor author;
    private final List<EmbedField> fields;

    public static Builder builder() {
        return new Builder();
    }

    public static DiscordEmbed fromJson(JsonObject json) {
        Builder builder = DiscordEmbed.builder();
        if (json.has("timestamp")) {
            builder.timestamp(OffsetDateTime.parse(json.get("timestamp").getAsString()));
        }
        if (json.has("color")) {
            builder.color(new Color(Integer.parseUnsignedInt(json.get("color").getAsString(), 16), true));
        }
        if (json.has("description")) {
            builder.description(json.get("description").getAsString());
        }
        if (json.has("thumbnail")) {
            builder.thumbnailUrl(json.get("thumbnail").getAsJsonObject().get("url").getAsString());
        }
        if (json.has("image")) {
            builder.imageUrl(json.get("image").getAsJsonObject().get("url").getAsString());
        }
        if (json.has("footer")) {
            builder.footer(EmbedFooter.fromJson(json.get("footer").getAsJsonObject()));
        }
        if (json.has("title")) {
            builder.title(EmbedTitle.fromJson(json.get("title").getAsJsonObject()));
        }
        if (json.has("author")) {
            builder.author(EmbedAuthor.fromJson(json.get("author").getAsJsonObject()));
        }
        if (json.has("fields")) {
            JsonArray fields = json.get("fields").getAsJsonArray();
            for (int i = 0; i < fields.size(); i++) {
                builder.field(EmbedField.fromJson(fields.get(i).getAsJsonObject()));
            }
        }
        return builder.build();
    }

    public DiscordEmbed(@Nullable OffsetDateTime timestamp, @Nullable Color color, @Nullable String description, @Nullable String thumbnailUrl, @Nullable String imageUrl, @Nullable DiscordEmbed.EmbedFooter footer, @Nullable DiscordEmbed.EmbedTitle title, @Nullable DiscordEmbed.EmbedAuthor author, @NotNull List<EmbedField> fields) {
        this.timestamp = timestamp;
        this.color = color;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.footer = footer;
        this.title = title;
        this.author = author;
        this.fields = Collections.unmodifiableList(fields);
    }

    @Nullable
    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    @Nullable
    public String getImageUrl() {
        return this.imageUrl;
    }

    @Nullable
    public OffsetDateTime getTimestamp() {
        return this.timestamp;
    }

    @Nullable
    public DiscordEmbed.EmbedTitle getTitle() {
        return this.title;
    }

    @Nullable
    public Color getColor() {
        return this.color;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Nullable
    public DiscordEmbed.EmbedFooter getFooter() {
        return this.footer;
    }

    @Nullable
    public DiscordEmbed.EmbedAuthor getAuthor() {
        return this.author;
    }

    @NotNull
    public List<EmbedField> getFields() {
        return this.fields;
    }

    public String toString() {
        return this.toJsonString();
    }

    @Override
    public String getType() {
        return "embed";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (this.description != null) {
            json.addProperty("description", this.description);
        }

        if (this.timestamp != null) {
            json.addProperty("timestamp", this.timestamp.toString());
        }

        if (this.color != null) {
            json.addProperty("color", Integer.toHexString(this.color.getRGB()));
        }

        if (this.author != null) {
            json.add("author", this.author.toJson());
        }

        if (this.footer != null) {
            json.add("footer", this.footer.toJson());
        }

        if (this.thumbnailUrl != null) {
            JsonObject thumbnail = new JsonObject();
            thumbnail.addProperty("url", this.thumbnailUrl);
            json.add("thumbnail", thumbnail);
        }

        if (this.imageUrl != null) {
            JsonObject image = new JsonObject();
            image.addProperty("url", this.imageUrl);
            json.add("image", image);
        }

        if (!this.fields.isEmpty()) {
            JsonArray fields = new JsonArray();
            for (EmbedField field : this.fields) {
                fields.add(field.toJson());
            }
            json.add("fields", fields);
        }

        if (this.title != null) {
            json.add("title", this.title.toJson());
        }

        return json;
    }

    public String toJsonString() {
        return this.toJson().toString();
    }

    @Override
    public Builder toBuilder() {
        return DiscordEmbed.builder()
                .description(this.description)
                .timestamp(this.timestamp)
                .color(this.color)
                .thumbnailUrl(this.thumbnailUrl)
                .imageUrl(this.imageUrl)
                .footer(this.footer)
                .title(this.title)
                .author(this.author)
                .field(this.fields);
    }

    public static class EmbedTitle implements JsonSerializable {

        private final String text;
        private final String url;

        public static EmbedTitle fromJson(JsonObject json) {
            return new EmbedTitle(json.get("text").getAsString(),
                    json.has("url") ? json.get("url").getAsString() : null);
        }

        public EmbedTitle(@NotNull String text, @Nullable String url) {
            this.text = Objects.requireNonNull(text);
            this.url = url;
        }

        @NotNull
        public String getText() {
            return this.text;
        }

        @Nullable
        public String getUrl() {
            return this.url;
        }

        public String toString() {
            return this.toJsonString();
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("text", this.text);
            if (this.url != null) {
                jsonObject.addProperty("url", this.url);
            }
            return jsonObject;
        }

        @Override
        public String toJsonString() {
            return this.toJson().toString();
        }

    }

    public static class EmbedFooter implements JsonSerializable {

        private final String text;
        private final String icon;

        public static EmbedFooter fromJson(JsonObject json) {
            return new EmbedFooter(json.get("text").getAsString(),
                    json.has("icon_url") ? json.get("icon_url").getAsString() : null);
        }

        public EmbedFooter(@NotNull String text, @Nullable String icon) {
            this.text = Objects.requireNonNull(text);
            this.icon = icon;
        }

        @NotNull
        public String getText() {
            return this.text;
        }

        @Nullable
        public String getIconUrl() {
            return this.icon;
        }

        public String toString() {
            return this.toJsonString();
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("text", this.text);
            if (this.icon != null) {
                jsonObject.addProperty("icon_url", this.icon);
            }
            return jsonObject;
        }

        public String toJsonString() {
            return this.toJson().toString();
        }

    }

    public static class EmbedAuthor implements JsonSerializable {

        private final String name;
        private final String iconUrl;
        private final String url;

        public static EmbedAuthor fromJson(JsonObject json) {
            return new EmbedAuthor(json.get("name").getAsString(),
                    json.has("icon_url") ? json.get("icon_url").getAsString() : null,
                    json.has("url") ? json.get("url").getAsString() : null);
        }

        public EmbedAuthor(@NotNull String name, @Nullable String iconUrl, @Nullable String url) {
            this.name = Objects.requireNonNull(name);
            this.iconUrl = iconUrl;
            this.url = url;
        }

        @NotNull
        public String getName() {
            return this.name;
        }

        @Nullable
        public String getIconUrl() {
            return this.iconUrl;
        }

        @Nullable
        public String getUrl() {
            return this.url;
        }

        public String toString() {
            return this.toJsonString();
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", this.name);
            if (this.iconUrl != null) {
                jsonObject.addProperty("icon_url", this.iconUrl);
            }
            if (this.url != null) {
                jsonObject.addProperty("url", this.url);
            }
            return jsonObject;
        }

        public String toJsonString() {
            return this.toJson().toString();
        }

    }

    public static class EmbedField implements JsonSerializable {

        private final boolean inline;
        private final String name;
        private final String value;

        public static EmbedField fromJson(JsonObject json) {
            return new EmbedField(json.get("name").getAsString(),
                    json.get("value").getAsString(),
                    json.get("inline").getAsBoolean());
        }

        public EmbedField(@NotNull String name, @NotNull String value, boolean inline) {
            this.inline = inline;
            this.name = Objects.requireNonNull(name);
            this.value = Objects.requireNonNull(value);
        }

        public boolean isInline() {
            return this.inline;
        }

        @NotNull
        public String getName() {
            return this.name;
        }

        @NotNull
        public String getValue() {
            return this.value;
        }

        public String toString() {
            return this.toJsonString();
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("inline", this.inline);
            jsonObject.addProperty("name", this.name);
            jsonObject.addProperty("value", this.value);
            return jsonObject;
        }

        public String toJsonString() {
            return this.toJson().toString();
        }

    }

    public static class Builder implements BuildableComponent.Builder<DiscordEmbed> {

        private OffsetDateTime timestamp;
        private Color color;
        private String description;
        private String thumbnailUrl;
        private String imageUrl;
        private EmbedFooter footer;
        private EmbedTitle title;
        private EmbedAuthor author;
        private final List<EmbedField> fields;

        public Builder() {
            this.fields = new ArrayList<>();
        }

        public Builder timestamp(TemporalAccessor timestamp) {
            if (timestamp instanceof Instant) {
                this.timestamp = OffsetDateTime.ofInstant((Instant)timestamp, ZoneId.of("UTC"));
            } else {
                this.timestamp = timestamp == null ? null : OffsetDateTime.from(timestamp);
            }
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder thumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder footer(EmbedFooter footer) {
            this.footer = footer;
            return this;
        }

        public Builder title(EmbedTitle title) {
            this.title = title;
            return this;
        }

        public Builder author(EmbedAuthor author) {
            this.author = author;
            return this;
        }

        public Builder field(EmbedField field) {
            if (this.fields.size() == 25) {
                throw new IllegalStateException("Cannot add more than 25 fields");
            } else {
                this.fields.add(field);
                return this;
            }
        }

        public Builder field(Collection<EmbedField> fields) {
            if (this.fields.size() == 25) {
                throw new IllegalStateException("Cannot add more than 25 fields");
            } else {
                this.fields.addAll(fields);
                return this;
            }
        }

        @Override
        public DiscordEmbed build() {
            return new DiscordEmbed(this.timestamp, this.color, this.description, this.thumbnailUrl, this.imageUrl, this.footer, this.title, this.author, this.fields);
        }

    }

}
