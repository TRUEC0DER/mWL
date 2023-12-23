package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.List;

public class CommandHelp {
    public static boolean execute(Audience sender) {
        List<String> helpMessage = ConfigModel.getMessageHelp();
        for (String message : helpMessage) {
            sender.sendMessage(MessageUtil.createWithoutPrefix(message));
        }
        return true;
    }
}
