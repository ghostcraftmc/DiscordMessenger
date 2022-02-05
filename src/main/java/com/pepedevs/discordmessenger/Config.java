package com.pepedevs.discordmessenger;

import com.pepedevs.radium.utils.configuration.Loadable;
import com.pepedevs.radium.utils.configuration.annotations.LoadableEntry;
import org.bukkit.configuration.ConfigurationSection;

public class Config implements Loadable {

    @LoadableEntry(key = "host", subsection = "redis")
    private String host;

    @LoadableEntry(key = "port", subsection = "redis")
    private int port;

    @LoadableEntry(key = "password", subsection = "redis")
    private String password;

    @Override
    public Loadable load(ConfigurationSection section) {
        return this.loadEntries(section);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

}
