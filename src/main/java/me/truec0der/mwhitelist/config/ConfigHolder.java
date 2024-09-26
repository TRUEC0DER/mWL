package me.truec0der.mwhitelist.config;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class ConfigHolder {
    private final Plugin plugin;
    private final File filePath;
    private String file;
    private String defaultFile;
    private File configFile;
    @Getter
    private YamlConfiguration config;

    public ConfigHolder(Plugin plugin, File filePath, String file) {
        this.plugin = plugin;
        this.filePath = filePath;
        this.file = file;
    }

    public ConfigHolder(Plugin plugin, File filePath, String file, String defaultFile) {
        this.plugin = plugin;
        this.filePath = filePath;
        this.file = file;
        this.defaultFile = defaultFile;
    }

    public void save() {
        save(config, configFile);
    }

    public void load() {
        if (defaultFile == null) load(filePath, file);
        if (defaultFile != null) load(filePath, file, defaultFile);
    }

    public void loadAndSave() {
        load();
        save();
    }

    public void reload() {
        loadAndSave();
        init();
    }

    public void reload(String file, String defaultFile) {
        this.file = file;
        this.defaultFile = defaultFile;

        loadAndSave();
        init();
    }

    private void load(File filePath, String file) {
        File configFile = new File(filePath, file);

        if (!configFile.exists()) {
            this.plugin.saveResource(file, false);
        }

        this.configFile = configFile;
        this.config = YamlConfiguration.loadConfiguration(configFile);

        copyDefaults(config, file);
    }

    private void load(File filePath, String file, String defaultFile) {
        File configFile = new File(filePath, file);

        InputStream resourcePath = plugin.getResource(defaultFile);

        String resultFile = (resourcePath != null) ? file : defaultFile;
        File resultConfigFile = new File(filePath, resultFile);

        if (!configFile.exists()) {
            configFile = (resourcePath != null) ? configFile : new File(filePath, defaultFile);
        }

        this.configFile = configFile;
        this.config = YamlConfiguration.loadConfiguration(configFile);

        if (!resultConfigFile.exists()) this.plugin.saveResource(resultFile, false);

        copyDefaults(config, resultFile);
    }

    private void save(YamlConfiguration config, File configFile) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyDefaults(YamlConfiguration config, String file) {
        try (InputStream defaultSettingsStream = plugin.getResource(file);
             InputStreamReader reader = new InputStreamReader(defaultSettingsStream)) {

            YamlConfiguration defaultSettings = YamlConfiguration.loadConfiguration(reader);

            defaultSettings.getKeys(true).stream()
                    .filter(key -> !config.contains(key))
                    .forEach(key -> config.set(key, defaultSettings.get(key)));

            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void init();
}