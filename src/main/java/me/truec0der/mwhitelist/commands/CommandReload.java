package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.MWhitelist;
import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class CommandReload {
    public static boolean execute(Audience sender) {
        ConfigManager.reloadConfig();
        ConfigModel.reloadConfig();
        Component reloadMessage = MessageUtil.createWithPrefix(ConfigModel.getMessagePluginReload());
        sender.sendMessage(reloadMessage);
        return true;
    }
}
