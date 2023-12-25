package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.MWhitelist;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.mongodb.MongoDBUserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommandAdd {
    private ConfigModel configModel;
    private MongoDBUserModel mongoDBUserModel;
    private MessageUtil messageUtil;

    public CommandAdd(ConfigModel configModel, MongoDBUserModel mongoDBUserModel, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.mongoDBUserModel = mongoDBUserModel;
    }

    public boolean execute(Audience sender, String[] args) {
        if (args.length < 2) {
            sendNeedMoreArgs(sender);
            return true;
        }
        String playerName = args[1];

        Document findUserInWhitelist = mongoDBUserModel.findByNickname(playerName);

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

        mongoDBUserModel.insertOne(playerName);

        Component addInWhitelist = messageUtil.create(configModel.getMessageWhitelistAddInfo(), addInWhitelistPlaceholders);
        sender.sendMessage(addInWhitelist);
    }
}
