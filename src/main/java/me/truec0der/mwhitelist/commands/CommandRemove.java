package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.mongodb.MongoDBUserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class CommandRemove {
    private ConfigModel configModel;
    private MongoDBUserModel mongoDBUserModel;
    private MessageUtil messageUtil;

    public CommandRemove(ConfigModel configModel, MongoDBUserModel mongoDBUserModel, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.mongoDBUserModel = mongoDBUserModel;
    }

    public boolean execute(Audience sender, String[] args) {
        if (args.length < 2) {
            sendNeedMoreArgsMessage(sender);
            return true;
        }

        String playerName = args[1];
        Document findUserInWhitelist = mongoDBUserModel.findByNickname(playerName);

        if (findUserInWhitelist == null) {
            sendNotInWhitelist(sender, playerName);
            return true;
        }

        removeFromWhitelist(sender, playerName);

        return true;
    }

    private void sendNeedMoreArgsMessage(Audience sender) {
        Component needMoreArgs = messageUtil.create(configModel.getMessageWhitelistRemoveNeedMoreArgs());
        sender.sendMessage(needMoreArgs);
    }

    private void sendNotInWhitelist(Audience sender, String playerName) {
        Map<String, String> notInWhitelistPlaceholders = new HashMap<>();
        notInWhitelistPlaceholders.put("player_name", playerName);
        Component notInWhitelist = messageUtil.create(configModel.getMessageWhitelistRemoveNotInWhitelist(), notInWhitelistPlaceholders);
        sender.sendMessage(notInWhitelist);
    }

    private void removeFromWhitelist(Audience sender, String playerName) {
        Map<String, String> removeFromWhitelistPlaceholders = new HashMap<>();
        removeFromWhitelistPlaceholders.put("player_name", playerName);

        mongoDBUserModel.deleteByNickname(playerName);

        Component removeFromWhitelist = messageUtil.create(configModel.getMessageWhitelistRemoveInfo(), removeFromWhitelistPlaceholders);
        sender.sendMessage(removeFromWhitelist);
    }
}
