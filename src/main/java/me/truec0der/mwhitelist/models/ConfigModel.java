package me.truec0der.mwhitelist.models;

import lombok.Getter;
import me.truec0der.mwhitelist.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigModel {
    @Getter
    private static Boolean whitelistStatus;
    @Getter
    private static String mongoUrl;
    @Getter
    private static String mongoName;
    @Getter
    private static String mongoCollectionUser;
    @Getter
    private static String messagePrefix;
    @Getter
    private static List<String> messageHelp;
    @Getter
    private static String messageWhitelistEnabled;
    @Getter
    private static String messageWhitelistDisabled;
    @Getter
    private static String messageWhitelistStatusInfo;
    @Getter
    private static String messageWhitelistStatusEnabled;
    @Getter
    private static String messageWhitelistStatusDisabled;
    @Getter
    private static String messageWhitelistNotIn;
    @Getter
    private static String messageWhitelistAddInfo;
    @Getter
    private static String messageWhitelistAddNeedMoreArgs;
    @Getter
    private static String messageWhitelistAddAlreadyWhitelisted;
    @Getter
    private static String messageWhitelistRemoveInfo;
    @Getter
    private static String messageWhitelistRemoveNeedMoreArgs;
    @Getter
    private static String messageWhitelistRemoveNotInWhitelist;
    @Getter
    private static String messageWhitelistListEmpty;
    @Getter
    private static String messageWhitelistListSeparator;
    @Getter
    private static String messageWhitelistListUser;
    @Getter
    private static String messageWhitelistListInfo;
    @Getter
    private static String messageNotPerms;
    @Getter
    private static String messagePluginReload;

    static {
        reloadConfig();
    }

    public static void reloadConfig() {
        FileConfiguration config = ConfigManager.getConfig();

        whitelistStatus = config.getBoolean("whitelist.status");
        mongoUrl = config.getString("database.mongo.url");
        mongoName = config.getString("database.mongo.name");
        mongoCollectionUser = config.getString("database.mongo.collections.users");
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