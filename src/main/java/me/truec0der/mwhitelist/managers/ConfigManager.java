package me.truec0der.mwhitelist.managers;

import lombok.Getter;
import me.truec0der.mwhitelist.MWhitelist;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private JavaPlugin instance;

    @Getter
    private FileConfiguration config;

    public ConfigManager(MWhitelist instance) {
        this.instance = instance;

        this.config = this.instance.getConfig();
        this.config.options().copyDefaults(true);
        this.instance.saveConfig();
    }

    public void reloadConfig() {
        this.instance.reloadConfig();
        this.config = this.instance.getConfig();
    }

    public void save() {
        this.instance.saveConfig();
    }
}
