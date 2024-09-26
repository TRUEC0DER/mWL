package me.truec0der.mwhitelist.service.database;

import com.mongodb.ConnectionString;
import me.truec0der.mwhitelist.impl.repository.json.JsonRepositoryImpl;
import me.truec0der.mwhitelist.impl.repository.json.player.JsonPlayerRepositoryImpl;
import me.truec0der.mwhitelist.impl.repository.mongo.MongoRepositoryImpl;
import me.truec0der.mwhitelist.impl.repository.mongo.player.MongoPlayerRepositoryImpl;
import me.truec0der.mwhitelist.interfaces.repository.PlayerRepository;
import me.truec0der.mwhitelist.interfaces.repository.json.JsonRepository;
import me.truec0der.mwhitelist.interfaces.repository.mongo.MongoRepository;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class DatabaseConnectionService {
    private MongoRepository mongoRepository;
    private JsonRepository jsonRepository;

    public PlayerRepository initMongoPlayerService(ConnectionString connectionString, String databaseName) {
        mongoRepository = new MongoRepositoryImpl(connectionString, databaseName);
        mongoRepository.init();
        return new MongoPlayerRepositoryImpl(mongoRepository);
    }

    public PlayerRepository initJsonPlayerService(Plugin plugin, File filePath, String file) {
        jsonRepository = new JsonRepositoryImpl(plugin, filePath, file);
        jsonRepository.init();
        return new JsonPlayerRepositoryImpl(jsonRepository);
    }

    public void closeMongoService() {
        if (mongoRepository == null) return;
        mongoRepository.closeConnection();
        mongoRepository = null;
    }

    public void closeJsonService() {
        if (jsonRepository == null) return;
        jsonRepository = null;
    }

    public void close() {
        closeMongoService();
        closeJsonService();
    }
}
