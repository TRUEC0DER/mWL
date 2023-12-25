package me.truec0der.mwhitelist;

import lombok.Getter;
import me.truec0der.mwhitelist.commands.CommandHandler;
import me.truec0der.mwhitelist.events.PlayerLoginEventListener;
import me.truec0der.mwhitelist.events.TabCompletionEventListener;
import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.managers.MongoDBManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.mongodb.MongoDBUserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MWhitelist extends JavaPlugin {
    private ConfigManager configManager;
    private ConfigModel configModel;

    private MongoDBManager mongoDBManager;
    private MongoDBUserModel mongoDBUserModel;

    private MessageUtil messageUtil;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configModel = new ConfigModel(configManager);

        mongoDBManager = new MongoDBManager(configModel.getMongoUrl(), configModel.getMongoName());
        mongoDBUserModel = new MongoDBUserModel(mongoDBManager, configModel);

        messageUtil = new MessageUtil(configModel);

        this.getCommand("mwhitelist").setExecutor(new CommandHandler(configManager, configModel, mongoDBUserModel, messageUtil));
        this.getCommand("mwhitelist").setTabCompleter(new TabCompletionEventListener());
        this.getServer().getPluginManager().registerEvents(new PlayerLoginEventListener(configModel, mongoDBUserModel, messageUtil), this);

        Bukkit.getServer().getLogger().info("[mWL] Database connected!");
        Bukkit.getServer().getLogger().info("[mWL] Plugin enabled!");
    }

    @Override
    public void onDisable() {
        if (mongoDBManager != null) {
            mongoDBManager.closeConnection();
        }

        Bukkit.getServer().getLogger().info("[mWL] Database disconnected!");
        Bukkit.getServer().getLogger().info("[mWL] Plugin disabled!");
    }
}