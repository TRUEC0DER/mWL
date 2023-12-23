package me.truec0der.mwhitelist.commands;

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
    public static boolean execute(Audience sender, String[] args) {
        if (args.length < 2) {
            sendNeedMoreArgs(sender);
            return true;
        }
        String playerName = args[1];

        Document findUserInWhitelist = MongoDBUserModel.findByNickname(playerName);

        if (findUserInWhitelist != null) {
            sendAlreadyInWhitelist(sender, playerName);
            return true;
        }

        addInWhitelist(sender, playerName);

        return true;
    }

    private static void sendNeedMoreArgs(Audience sender) {
        Component needMoreArgs = MessageUtil.createWithPrefix(ConfigModel.getMessageWhitelistAddNeedMoreArgs());
        sender.sendMessage(needMoreArgs);
    }

    private static void sendAlreadyInWhitelist(Audience sender, String playerName) {
        Map<String, String> alreadyInWhitelistPlaceholders = new HashMap<>();
        alreadyInWhitelistPlaceholders.put("player_name", playerName);
        Component alreadyInWhitelist = MessageUtil.createWithPrefix(ConfigModel.getMessageWhitelistAddAlreadyWhitelisted(), alreadyInWhitelistPlaceholders);
        sender.sendMessage(alreadyInWhitelist);
    }

    private static void addInWhitelist(Audience sender, String playerName) {
        Map<String, String> addInWhitelistPlaceholders = new HashMap<>();
        addInWhitelistPlaceholders.put("player_name", playerName);

        MongoDBUserModel.insertOne(playerName);

        Component addInWhitelist = MessageUtil.createWithPrefix(ConfigModel.getMessageWhitelistAddInfo(), addInWhitelistPlaceholders);
        sender.sendMessage(addInWhitelist);
    }
}
