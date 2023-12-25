package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.MWhitelist;
import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public class CommandToggle {
    private ConfigManager configManager;
    private ConfigModel configModel;
    private MessageUtil messageUtil;

    public CommandToggle(ConfigManager configManager, ConfigModel configModel, MessageUtil messageUtil) {
        this.configManager = configManager;
        this.configModel = configModel;
        this.messageUtil = messageUtil;
    }

    private void sendStatusMessage(Audience sender) {
        boolean whitelistStatus = configModel.getWhitelistStatus();
        Map<String, String> statusPlaceholders = new HashMap<>();
        statusPlaceholders.put(
                "whitelist_status",
                whitelistStatus ? configModel.getMessageWhitelistStatusEnabled() : configModel.getMessageWhitelistStatusDisabled()
        );
        Component statusMessage = messageUtil.create(
                configModel.getMessageWhitelistStatusInfo(),
                statusPlaceholders
        );
        sender.sendMessage(statusMessage);
    }

    private void enableWhitelist(Audience sender) {
        configManager.getConfig().set("whitelist.status", true);
        configManager.save();

        configManager.reloadConfig();
        configModel.reloadConfig();

        Component enabledMessage = messageUtil.create(
                configModel.getMessageWhitelistEnabled()
        );

        sender.sendMessage(enabledMessage);
    }

    private void disableWhitelist(Audience sender) {
        configManager.getConfig().set("whitelist.status", false);
        configManager.save();

        configManager.reloadConfig();
        configModel.reloadConfig();

        Component disableMessage = messageUtil.create(
                configModel.getMessageWhitelistDisabled()
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
