package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.MWhitelist;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.List;

public class CommandHelp {
    private ConfigModel configModel;
    private MessageUtil messageUtil;

    public CommandHelp(ConfigModel configModel, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
    }

    public boolean execute(Audience sender) {
        List<String> helpMessage = configModel.getMessageHelp();
        for (String message : helpMessage) {
            sender.sendMessage(messageUtil.create(message));
        }
        return true;
    }
}

