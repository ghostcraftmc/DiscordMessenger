package org.zibble.discordmessenger.components;

public interface BuildableComponent<T extends BuildableComponent.Builder> {

    T toBuilder();

    interface Builder<T extends BuildableComponent> {

        T build();

    }

}
