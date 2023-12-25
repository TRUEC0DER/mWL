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
    private ConfigModel configModel;
    private MongoDBUserModel mongoDBUserModel;
    private MessageUtil messageUtil;

    public CommandList(ConfigModel configModel, MongoDBUserModel mongoDBUserModel, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.mongoDBUserModel = mongoDBUserModel;
    }

    private void sendEmptyWhitelistMessage(Audience sender) {
        Component emptyMessage = messageUtil.create(configModel.getMessageWhitelistListEmpty());
        sender.sendMessage(emptyMessage);
    }

    private void sendWhitelistInfoMessage(Audience sender, String playerList) {
        Map<String, String> infoPlaceholders = new HashMap<>();
        infoPlaceholders.put("whitelist_list", playerList);
        Component whitelistInfo = messageUtil.create(configModel.getMessageWhitelistListInfo(), infoPlaceholders);
        sender.sendMessage(whitelistInfo);
    }

    private String getPlayerList(List<String> playerList) {
        return playerList.stream()
                .map(playerName -> configModel.getMessageWhitelistListUser().replace("%player_name%", playerName))
                .collect(Collectors.joining(configModel.getMessageWhitelistListSeparator()));
    }


    public boolean execute(Audience sender) {
        FindIterable<Document> documents = mongoDBUserModel.findAll();
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
