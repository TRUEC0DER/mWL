package me.truec0der.mwhitelist.managers;

import lombok.Getter;
import me.truec0der.mwhitelist.MWhitelist;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static MWhitelist instance;

    @Getter
    private static FileConfiguration config;

    public static void init() {
        instance = MWhitelist.getInstance();
        config = instance.getConfig();

        config.options().copyDefaults(true);
        instance.saveConfig();
    }

    public static void reloadConfig() {
        instance.reloadConfig();

        instance = MWhitelist.getInstance();
        config = instance.getConfig();
    }

    public static void save() {
        instance.saveConfig();
    }
}
