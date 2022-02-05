package com.pepedevs.discordmessenger.messagable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DiscordMessage implements Message, BuildableComponent<DiscordMessage.Builder> {

    public static final int MAX_FILES = 10;
    public static final int MAX_EMBEDS = 10;

    protected final String content;
    protected final Collection<DiscordEmbed> embeds;
    protected final boolean isTTS;
    protected final Collection<MentionType> allowedMentions = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public static DiscordMessage fromJson(JsonObject json) {
        String content = json.has("content") ? json.get("content").getAsString() : null;
        boolean isTTS = json.has("tts") && json.get("tts").getAsBoolean();
        List<DiscordEmbed> embeds = new ArrayList<>();
        if (json.has("embeds")) {
            JsonArray embedsJson = json.get("embeds").getAsJsonArray();
            for (int i = 0; i < embedsJson.size(); ++i) {
                embeds.add(DiscordEmbed.fromJson(embedsJson.get(i).getAsJsonObject()));
            }
        }
        Collection<MentionType> allowedMentions = new ArrayList<>();
        for (JsonElement element : json.get("allowed_mentions").getAsJsonArray()) {
            allowedMentions.add(MentionType.fromJson(element.getAsJsonObject()));
        }
        return new DiscordMessage(content, embeds, isTTS, allowedMentions);
    }

    public DiscordMessage(String content, Collection<DiscordEmbed> embeds, boolean isTTS, Collection<MentionType> allowedMentions) {
        this.content = content;
        this.embeds = embeds;
        this.isTTS = isTTS;
        this.allowedMentions.addAll(allowedMentions);
    }

    @Nullable
    public String getContent() {
        return this.content;
    }

    @NotNull
    public Collection<DiscordEmbed> getEmbeds() {
        return this.embeds == null ? Collections.emptyList() : this.embeds;
    }

    public boolean isTTS() {
        return this.isTTS;
    }

    @NotNull
    public static DiscordMessage embeds(@NotNull DiscordEmbed first, @NotNull DiscordEmbed... embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (embeds.length >= 10) {
            throw new IllegalArgumentException("Cannot add more than 10 embeds to a message");
        } else {
            DiscordEmbed[] var2 = embeds;
            int var3 = embeds.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                DiscordEmbed e = var2[var4];
                Objects.requireNonNull(e);
            }

            List<DiscordEmbed> list = new ArrayList<>(1 + embeds.length);
            list.add(first);
            Collections.addAll(list, embeds);
            return new DiscordMessage(null, list, false, Collections.singleton(MentionType.EVERYONE));
        }
    }

    @NotNull
    public static DiscordMessage embeds(@NotNull Collection<DiscordEmbed> embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (embeds.size() > 10) {
            throw new IllegalArgumentException("Cannot add more than 10 embeds to a message");
        } else if (embeds.isEmpty()) {
            throw new IllegalArgumentException("Cannot build an empty message");
        } else {
            embeds.forEach(Objects::requireNonNull);
            return new DiscordMessage(null, new ArrayList<>(embeds), false, Collections.singleton(MentionType.EVERYONE));
        }
    }

    @Override
    public String getType() {
        return "message";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (this.content != null) {
            json.addProperty("content", this.content);
        }
        if (this.embeds != null && !this.embeds.isEmpty()) {
            JsonArray embeds = new JsonArray();
            this.embeds.stream().limit(MAX_EMBEDS).forEach(embed -> embeds.add(embed.toJson()));
            json.add("embeds", embeds);
        }
        json.addProperty("tts", this.isTTS);
        JsonArray allowedMentions = new JsonArray();
        this.allowedMentions.forEach(mention -> allowedMentions.add(mention.toJson()));
        json.add("allowed_mentions", allowedMentions);

        return json;
    }

    @Override
    public String toJsonString() {
        return this.toJson().toString();
    }

    @Override
    public Builder toBuilder() {
        return DiscordMessage.builder()
                .content(this.content)
                .embeds(this.embeds)
                .tts(this.isTTS)
                .allowedMentions(this.allowedMentions);

    }

    public static class Builder implements BuildableComponent.Builder<DiscordMessage> {

        private StringBuilder content = new StringBuilder();
        private List<DiscordEmbed> embeds = new LinkedList<>();
        private boolean isTTS;
        private Collection<MentionType> allowedMentions = new ArrayList<>();

        public Builder() {
        }

        public boolean isEmpty() {
            return this.content.length() == 0 && this.embeds.isEmpty();
        }

        public Builder reset() {
            this.content.setLength(0);
            this.resetEmbeds();
            this.isTTS = false;
            return this;
        }

        public Builder resetEmbeds() {
            this.embeds.clear();
            return this;
        }

        public Builder content(String content) {
            if (content != null && content.length() > 2000) {
                throw new IllegalArgumentException("Content may not exceed 2000 characters!");
            } else {
                this.content.setLength(0);
                if (content != null && !content.isEmpty()) {
                    this.content.append(content);
                }

                return this;
            }
        }

        public Builder appendContent(String content) {
            if (this.content.length() + content.length() > 2000) {
                throw new IllegalArgumentException("Content may not exceed 2000 characters!");
            } else {
                this.content.append(content);
                return this;
            }
        }

        public Builder embed(DiscordEmbed... embeds) {
            return this.embeds(Arrays.asList(embeds));
        }

        public Builder embeds(Collection<DiscordEmbed> embeds) {
            if (this.embeds.size() + embeds.size() > 10) {
                throw new IllegalStateException("Cannot add more than 10 embeds to a message");
            } else {
                this.embeds.addAll(embeds);

                return this;
            }
        }

        public Builder tts(boolean isTTS) {
            this.isTTS = isTTS;
            return this;
        }

        public Builder allowedMentions(MentionType... mentionTypes) {
            return this.allowedMentions(Arrays.asList(mentionTypes));
        }

        public Builder allowedMentions(Collection<MentionType> mentionTypes) {
            this.allowedMentions.addAll(mentionTypes);
            return this;
        }

        @Override
        public DiscordMessage build() {
            return new DiscordMessage(this.content.toString(), this.embeds, this.isTTS, this.allowedMentions);
        }

    }

}
