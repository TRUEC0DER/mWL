package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

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
        String statusMessage = whitelistStatus
                ? configModel.getMessageWhitelistStatusEnabled()
                : configModel.getMessageWhitelistStatusDisabled();

        Component statusMessageComponent = messageUtil.create(
                configModel.getMessageWhitelistStatusInfo(),
                Map.of("whitelist_status", statusMessage)
        );

        sender.sendMessage(statusMessageComponent);
    }

    private void toggleWhitelist(Audience sender, boolean enable) {
        configManager.getConfig().set("whitelist.status", enable);
        configManager.save();

        configManager.reloadConfig();
        configModel.reloadConfig();

        Component message = messageUtil.create(
                enable ? configModel.getMessageWhitelistEnabled() : configModel.getMessageWhitelistDisabled()
        );

        sender.sendMessage(message);
    }

    public boolean execute(Audience sender, String[] args) {
        if (args.length < 2) {
            sendStatusMessage(sender);
            return true;
        }

        String toggle = args[1].toLowerCase();

        switch (toggle) {
            case "on":
                toggleWhitelist(sender, true);
                return true;
            case "off":
                toggleWhitelist(sender, false);
                return true;
            default:
                sendStatusMessage(sender);
                return true;
        }
    }
}
