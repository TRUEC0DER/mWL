package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public class CommandToggle {
    private static void sendStatusMessage(Audience sender) {
        boolean whitelistStatus = ConfigModel.getWhitelistStatus();
        Map<String, String> statusPlaceholders = new HashMap<>();
        statusPlaceholders.put(
                "whitelist_status",
                whitelistStatus ? ConfigModel.getMessageWhitelistStatusEnabled() : ConfigModel.getMessageWhitelistStatusDisabled()
        );
        Component statusMessage = MessageUtil.createWithPrefix(
                ConfigModel.getMessageWhitelistStatusInfo(),
                statusPlaceholders
        );
        sender.sendMessage(statusMessage);
    }

    private static void enableWhitelist(Audience sender) {
        ConfigManager.getConfig().set("whitelist.status", true);
        ConfigManager.save();

        ConfigManager.reloadConfig();
        ConfigModel.reloadConfig();

        Component enabledMessage = MessageUtil.createWithPrefix(
                ConfigModel.getMessageWhitelistEnabled()
        );

        sender.sendMessage(enabledMessage);
    }

    private static void disableWhitelist(Audience sender) {
        ConfigManager.getConfig().set("whitelist.status", false);
        ConfigManager.save();

        ConfigManager.reloadConfig();
        ConfigModel.reloadConfig();

        Component disableMessage = MessageUtil.createWithPrefix(
                ConfigModel.getMessageWhitelistDisabled()
        );

        sender.sendMessage(disableMessage);
    }

    public boolean execute(Audience sender, String[] args) {
        if (args.length < 2) {
            sendStatusMessage(sender);
            return true;
        }

        String toggle = args[1];

        switch (toggle) {
            case "on":
                enableWhitelist(sender);
                return true;
            case "off":
                disableWhitelist(sender);
                return true;
            default:
                sendStatusMessage(sender);
                return true;
        }
    }
}
