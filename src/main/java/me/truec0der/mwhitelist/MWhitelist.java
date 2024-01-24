package me.truec0der.mwhitelist;

import me.truec0der.mwhitelist.commands.CommandHandler;
import me.truec0der.mwhitelist.database.Database;
import me.truec0der.mwhitelist.database.MongoDBDatabase;
import me.truec0der.mwhitelist.database.YamlDatabase;
import me.truec0der.mwhitelist.events.PlayerLoginEventListener;
import me.truec0der.mwhitelist.events.TabCompletionEventListener;
import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.managers.database.MongoDBManager;
import me.truec0der.mwhitelist.managers.database.YamlDBManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import me.truec0der.mwhitelist.utils.MetricsUtil;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class MWhitelist extends JavaPlugin {
    private static MWhitelist instance;
    private MetricsUtil metricsUtil;
    private ConfigManager configManager;
    private ConfigModel configModel;
    private Database database;
    private MessageUtil messageUtil;
    private MongoDBManager mongoDBManager;
    private YamlDBManager yamlDBManager;

    public static void reloadPlugin() {
        instance.reloadConfig();
        instance.onDisable();
        instance.onEnable();
    }

    @Override
    public void onEnable() {
        instance = this;

        initializeConfig();
        initializeDatabase();
        initializeMessageUtil();
        registerCommandsAndEvents();

        setupMetrics();

        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        closeDatabaseConnection();
        HandlerList.unregisterAll(this);
        shutdownMetrics();

        database = null;
        mongoDBManager = null;
        yamlDBManager = null;

        getLogger().info("Plugin disabled!");
    }

    private void initializeConfig() {
        configManager = new ConfigManager(this);
        configModel = new ConfigModel(configManager);
    }

    private void initializeDatabase() {
        String databaseType = configModel.getDatabaseType().toLowerCase();
        switch (databaseType) {
            case "mongodb":
                initializeMongoDatabase();
                break;
            default:
                initializeYamlDatabase();
                break;
        }
        getLogger().info("Database " + databaseType.toUpperCase() + " launched!");
    }

    private void initializeMongoDatabase() {
        mongoDBManager = new MongoDBManager(configModel.getMongoUrl(), configModel.getMongoName());
        database = new MongoDBDatabase(mongoDBManager, configModel);
    }

    private void initializeYamlDatabase() {
        yamlDBManager = new YamlDBManager(this, "whitelist.yml");
        database = new YamlDatabase(yamlDBManager, configModel);
    }

    private void initializeMessageUtil() {
        messageUtil = new MessageUtil(configModel);
    }

    private void registerCommandsAndEvents() {
        String commandLabel = "mwhitelist";
        getCommand(commandLabel).setExecutor(new CommandHandler(configManager, configModel, database, messageUtil));
        getCommand(commandLabel).setTabCompleter(new TabCompletionEventListener(database));
        getServer().getPluginManager().registerEvents(
                (new PlayerLoginEventListener(configModel, database, messageUtil)), this);
    }

    private void closeDatabaseConnection() {
        if (mongoDBManager != null) {
            mongoDBManager.closeConnection();
            getLogger().info("Database MongoDB disconnected!");
        }
    }

    private void setupMetrics() {
        int pluginId = 20746; // <-- Replace with the id of your plugin!
        metricsUtil = new MetricsUtil(this, pluginId);
    }

    private void shutdownMetrics() {
        metricsUtil.shutdown();
    }
}
