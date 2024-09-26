package me.truec0der.mwhitelist.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.config.configs.LangConfig;
import me.truec0der.mwhitelist.config.configs.MainConfig;
import org.bukkit.plugin.Plugin;

import java.io.File;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigRegister {
    final Plugin plugin;
    @Getter
    MainConfig mainConfig;
    @Getter
    LangConfig langConfig;

    public ConfigRegister(Plugin plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        mainConfig = new MainConfig(plugin, new File(plugin.getDataFolder().getPath()), "config.yml");
        langConfig = new LangConfig(plugin, new File(plugin.getDataFolder().getPath()), String.format("messages/lang_%s.yml", mainConfig.getLocale()), "messages/lang_en.yml");
    }
}
