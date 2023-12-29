package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.MWhitelist;
import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class CommandReload {
    private ConfigManager configManager;
    private ConfigModel configModel;
    private MessageUtil messageUtil;

    public CommandReload(ConfigManager configManager, ConfigModel configModel, MessageUtil messageUtil) {
        this.configManager = configManager;
        this.configModel = configModel;
        this.messageUtil = messageUtil;
    }

    public boolean execute(Audience sender) {
//        configManager.reloadConfig();
//        configModel.reloadConfig();
        MWhitelist.reloadPlugin();
        Component reloadMessage = messageUtil.create(configModel.getMessagePluginReload());
        sender.sendMessage(reloadMessage);
        return true;
    }
}
