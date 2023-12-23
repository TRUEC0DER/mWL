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
    public static boolean execute(Audience sender, String[] args) {
        if (args.length < 2) {
            sendNeedMoreArgsMessage(sender);
            return true;
        }

        String playerName = args[1];
        Document findUserInWhitelist = MongoDBUserModel.findByNickname(playerName);

        if (findUserInWhitelist == null) {
            sendNotInWhitelist(sender, playerName);
            return true;
        }

        removeFromWhitelist(sender, playerName);

        return true;
    }

    private static void sendNeedMoreArgsMessage(Audience sender) {
        Component needMoreArgs = MessageUtil.createWithPrefix(ConfigModel.getMessageWhitelistRemoveNeedMoreArgs());
        sender.sendMessage(needMoreArgs);
    }

    private static void sendNotInWhitelist(Audience sender, String playerName) {
        Map<String, String> notInWhitelistPlaceholders = new HashMap<>();
        notInWhitelistPlaceholders.put("player_name", playerName);
        Component notInWhitelist = MessageUtil.createWithPrefix(ConfigModel.getMessageWhitelistRemoveNotInWhitelist(), notInWhitelistPlaceholders);
        sender.sendMessage(notInWhitelist);
    }

    private static void removeFromWhitelist(Audience sender, String playerName) {
        Map<String, String> removeFromWhitelistPlaceholders = new HashMap<>();
        removeFromWhitelistPlaceholders.put("player_name", playerName);

        MongoDBUserModel.deleteByNickname(playerName);

        Component removeFromWhitelist = MessageUtil.createWithPrefix(ConfigModel.getMessageWhitelistRemoveInfo(), removeFromWhitelistPlaceholders);
        sender.sendMessage(removeFromWhitelist);
    }
}
