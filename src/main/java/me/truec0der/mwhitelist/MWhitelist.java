package me.truec0der.mwhitelist;

import me.truec0der.mwhitelist.command.CommandHandler;
import me.truec0der.mwhitelist.command.CommandController;
import me.truec0der.mwhitelist.config.ConfigRegister;
import me.truec0der.mwhitelist.impl.repository.RepositoryRegister;
import me.truec0der.mwhitelist.listener.PlayerJoinListener;
import me.truec0der.mwhitelist.misc.ThreadExecutor;
import me.truec0der.mwhitelist.model.enums.database.DatabaseType;
import me.truec0der.mwhitelist.service.ServiceRegister;
import me.truec0der.mwhitelist.service.database.DatabaseConnectionService;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class MWhitelist extends JavaPlugin {
    private ConfigRegister configRegister;
    private RepositoryRegister repositoryRegister;
    private ServiceRegister serviceRegister;
    private DatabaseConnectionService databaseConnectionService;
    private ThreadExecutor threadExecutor;

    @Override
    public void onEnable() {
        initConfig();
        initDatabaseConnection();
        initRepository();
        initService();
        initCommand();
        initListener();
        initMetrics();

        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        databaseConnectionService.close();
        serviceRegister.getWhitelistScheduleService().destroyExecutor();
        getLogger().info("Plugin disabled!");
    }

    private void initConfig() {
        configRegister = new ConfigRegister(this);
    }

    private void initDatabaseConnection() {
        databaseConnectionService = new DatabaseConnectionService();
    }

    private void initRepository() {
        DatabaseType databaseType = DatabaseType.valueOf("JSON");

        repositoryRegister = new RepositoryRegister(this, databaseConnectionService);
        repositoryRegister.init(databaseType, configRegister.getMainConfig().getMongoUrl(), configRegister.getMainConfig().getMongoCollectionUser());

        getLogger().info("Database " + databaseType.name() + " loaded!");
    }

    private void initService() {
        threadExecutor = new ThreadExecutor(this);
        serviceRegister = new ServiceRegister(repositoryRegister, configRegister, threadExecutor);
    }

    private void initCommand() {
        CommandController commandManager = new CommandController();

        CommandHandler commandHandler = new CommandHandler(commandManager, configRegister, serviceRegister);
        commandHandler.init();

        getCommand("mwhitelist").setExecutor(commandHandler);
        getCommand("mwhitelist").setTabCompleter(commandHandler);
    }

    private void initListener() {
        PlayerJoinListener playerJoinListener = new PlayerJoinListener(serviceRegister);
        getServer().getPluginManager().registerEvents(playerJoinListener, this);
    }

    private void initMetrics() {
        int pluginId = 20857;
        Metrics metrics = new Metrics(this, pluginId);
    }
}
