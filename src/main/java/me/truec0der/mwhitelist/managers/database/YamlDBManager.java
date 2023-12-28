package me.truec0der.mwhitelist.managers.database;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class YamlDBManager {
    private JavaPlugin instance;
    private File yamlDatabaseFile;

    @Getter
    private YamlConfiguration yamlDatabase;

    public YamlDBManager(JavaPlugin instance, String filePath) {
        this.instance = instance;

        this.yamlDatabaseFile = new File(this.instance.getDataFolder(), filePath);

        if (!yamlDatabaseFile.exists()) {
            instance.saveResource(filePath, false);
        }

        this.yamlDatabase = YamlConfiguration.loadConfiguration(yamlDatabaseFile);
    }

    public void save() {
        try {
            yamlDatabase.save(yamlDatabaseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
