package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.database.Database;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public class CommandAdd {
    private ConfigModel configModel;
    private Database database;
    private MessageUtil messageUtil;

    public CommandAdd(ConfigModel configModel, Database database, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.database = database;
    }

    public boolean execute(Audience sender, String[] args) {
        if (args.length < 2) {
            sendNeedMoreArgs(sender);
            return true;
        }
        String playerName = args[1];

        UserModel findUserInWhitelist = database.getUser(playerName);

        if (findUserInWhitelist != null) {
            sendAlreadyInWhitelist(sender, playerName);
            return true;
        }

        addInWhitelist(sender, playerName);

        return true;
    }

    private void sendNeedMoreArgs(Audience sender) {
        Component needMoreArgs = messageUtil.create(configModel.getMessageWhitelistAddNeedMoreArgs());
        sender.sendMessage(needMoreArgs);
    }

    private void sendAlreadyInWhitelist(Audience sender, String playerName) {
        Map<String, String> alreadyInWhitelistPlaceholders = new HashMap<>();
        alreadyInWhitelistPlaceholders.put("player_name", playerName);
        Component alreadyInWhitelist = messageUtil.create(configModel.getMessageWhitelistAddAlreadyWhitelisted(), alreadyInWhitelistPlaceholders);
        sender.sendMessage(alreadyInWhitelist);
    }

    private void addInWhitelist(Audience sender, String playerName) {
        Map<String, String> addInWhitelistPlaceholders = new HashMap<>();
        addInWhitelistPlaceholders.put("player_name", playerName);

        database.createUser(playerName);

        Component addInWhitelist = messageUtil.create(configModel.getMessageWhitelistAddInfo(), addInWhitelistPlaceholders);
        sender.sendMessage(addInWhitelist);
    }
}
