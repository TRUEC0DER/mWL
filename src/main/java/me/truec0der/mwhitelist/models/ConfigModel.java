package me.truec0der.mwhitelist.models;

import lombok.Getter;
import me.truec0der.mwhitelist.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigModel {
    private ConfigManager configManager;

    @Getter
    private Boolean whitelistStatus;
    @Getter
    private String databaseType;
    @Getter
    private String mongoUrl;
    @Getter
    private String mongoName;
    @Getter
    private String mongoCollectionUser;
    @Getter
    private String messagePrefix;
    @Getter
    private List<String> messageHelp;
    @Getter
    private String messageWhitelistEnabled;
    @Getter
    private String messageWhitelistDisabled;
    @Getter
    private String messageWhitelistStatusInfo;
    @Getter
    private String messageWhitelistStatusEnabled;
    @Getter
    private String messageWhitelistStatusDisabled;
    @Getter
    private String messageWhitelistNotIn;
    @Getter
    private String messageWhitelistAddInfo;
    @Getter
    private String messageWhitelistAddNeedMoreArgs;
    @Getter
    private String messageWhitelistAddAlreadyWhitelisted;
    @Getter
    private String messageWhitelistRemoveInfo;
    @Getter
    private String messageWhitelistRemoveNeedMoreArgs;
    @Getter
    private String messageWhitelistRemoveNotInWhitelist;
    @Getter
    private String messageWhitelistListEmpty;
    @Getter
    private String messageWhitelistListSeparator;
    @Getter
    private String messageWhitelistListUser;
    @Getter
    private String messageWhitelistListInfo;
    @Getter
    private String messageNotPerms;
    @Getter
    private String messagePluginReload;

    public ConfigModel(ConfigManager configManager) {
        this.configManager = configManager;
        reloadConfig();
    }

    public void reloadConfig() {
        FileConfiguration config = this.configManager.getConfig();

        whitelistStatus = config.getBoolean("whitelist.status");
        databaseType = config.getString("database.type");
        mongoUrl = config.getString("database.mongodb.url");
        mongoName = config.getString("database.mongodb.name");
        mongoCollectionUser = config.getString("database.mongodb.collections.users");
        messagePrefix = config.getString("messages.prefix");
        messageHelp = config.getStringList("messages.help");
        messageWhitelistEnabled = config.getString("messages.whitelist.toggle.enabled");
        messageWhitelistDisabled = config.getString("messages.whitelist.toggle.disabled");
        messageWhitelistStatusInfo = config.getString("messages.whitelist.toggle.status.info");
        messageWhitelistStatusEnabled = config.getString("messages.whitelist.toggle.status.enabled");
        messageWhitelistStatusDisabled = config.getString("messages.whitelist.toggle.status.disabled");
        messageWhitelistNotIn = config.getString("messages.whitelist.not-in-whitelist");
        messageWhitelistAddInfo = config.getString("messages.whitelist.add.info");
        messageWhitelistAddNeedMoreArgs = config.getString("messages.whitelist.add.need-more-args");
        messageWhitelistAddAlreadyWhitelisted = config.getString("messages.whitelist.add.already-whitelisted");
        messageWhitelistRemoveInfo = config.getString("messages.whitelist.remove.info");
        messageWhitelistRemoveNeedMoreArgs = config.getString("messages.whitelist.remove.need-more-args");
        messageWhitelistRemoveNotInWhitelist = config.getString("messages.whitelist.remove.not-in-whitelist");
        messageWhitelistListEmpty = config.getString("messages.whitelist.list.empty");
        messageWhitelistListSeparator = config.getString("messages.whitelist.list.separator");
        messageWhitelistListUser = config.getString("messages.whitelist.list.user");
        messageWhitelistListInfo = config.getString("messages.whitelist.list.info");
        messageNotPerms = config.getString("messages.not-perms");
        messagePluginReload = config.getString("messages.whitelist.reload");
    }
}