package me.truec0der.mwhitelist.impl.repository;

import com.mongodb.ConnectionString;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.interfaces.repository.PlayerRepository;
import me.truec0der.mwhitelist.model.enums.database.DatabaseType;
import me.truec0der.mwhitelist.service.database.DatabaseConnectionService;
import org.bukkit.plugin.Plugin;

import java.io.File;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepositoryRegister {
    Plugin plugin;
    DatabaseConnectionService databaseConnectionService;
    @Getter
    PlayerRepository playerRepository;

    public RepositoryRegister(Plugin plugin, DatabaseConnectionService databaseConnectionService) {
        this.plugin = plugin;
        this.databaseConnectionService = databaseConnectionService;
    }

    public void init(DatabaseType databaseType, String mongoUrl, String mongoPlayerCollection) {
        databaseConnectionService.close();

        switch (databaseType) {
            case MONGO: {
                ConnectionString connectionString = new ConnectionString(mongoUrl);
                playerRepository = databaseConnectionService.initMongoPlayerService(connectionString, mongoPlayerCollection);
                break;
            }
            default: {
                String fileName = "whitelist.json";
                File dataFolder = plugin.getDataFolder();
                File databaseFile = new File(dataFolder, fileName);
                playerRepository = databaseConnectionService.initJsonPlayerService(plugin, databaseFile, fileName);
            }
        }
    }
}
