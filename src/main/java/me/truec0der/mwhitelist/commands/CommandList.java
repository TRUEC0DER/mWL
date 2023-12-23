package me.truec0der.mwhitelist.commands;

import com.mongodb.client.FindIterable;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.mongodb.MongoDBUserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class CommandList {
    private static void sendEmptyWhitelistMessage(Audience sender) {
        Component emptyMessage = MessageUtil.createWithPrefix(ConfigModel.getMessageWhitelistListEmpty());
        sender.sendMessage(emptyMessage);
    }

    private static void sendWhitelistInfoMessage(Audience sender, String playerList) {
        Map<String, String> infoPlaceholders = new HashMap<>();
        infoPlaceholders.put("whitelist_list", playerList);
        Component whitelistInfo = MessageUtil.createWithPrefix(ConfigModel.getMessageWhitelistListInfo(), infoPlaceholders);
        sender.sendMessage(whitelistInfo);
    }

    private static String getPlayerList(List<String> playerList) {
        return playerList.stream()
                .map(playerName -> ConfigModel.getMessageWhitelistListUser().replace("%player_name%", playerName))
                .collect(Collectors.joining(ConfigModel.getMessageWhitelistListSeparator()));
    }


    public boolean execute(Audience sender) {
        FindIterable<Document> documents = MongoDBUserModel.findAll();
        List<String> whitelist = documents.map(doc ->
                doc.getString("nickname")
        ).into(new ArrayList<>());

        if (whitelist.isEmpty()) {
            sendEmptyWhitelistMessage(sender);
            return true;
        }

        String playerList = getPlayerList(whitelist);
        sendWhitelistInfoMessage(sender, playerList);

        return true;
    }
}
