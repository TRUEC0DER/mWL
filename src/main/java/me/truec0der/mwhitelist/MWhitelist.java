package me.truec0der.mwhitelist;

import lombok.Getter;
import me.truec0der.mwhitelist.commands.CommandHandler;
import me.truec0der.mwhitelist.events.PlayerLoginEventListener;
import me.truec0der.mwhitelist.events.TabCompletionEventListener;
import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.managers.MongoDBManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.mongodb.MongoDBUserModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MWhitelist extends JavaPlugin {

    @Getter
    private static MWhitelist instance;

    @Getter
    private static MongoDBManager mongoDBManager;

    @Override
    public void onEnable() {
        instance = this;

        ConfigManager.init();

        mongoDBManager = new MongoDBManager(ConfigModel.getMongoUrl(), ConfigModel.getMongoName());

        this.getCommand("mwhitelist").setExecutor(new CommandHandler());
        this.getCommand("mwhitelist").setTabCompleter(new TabCompletionEventListener());
        this.getServer().getPluginManager().registerEvents(new PlayerLoginEventListener(), this);

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