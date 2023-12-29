package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.database.Database;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandList {
    private ConfigModel configModel;
    private Database database;
    private MessageUtil messageUtil;
    private int playerCount;

    public CommandList(ConfigModel configModel, Database database, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.database = database;
    }

    private void sendEmptyWhitelistMessage(Audience sender) {
        Component emptyMessage = messageUtil.create(configModel.getMessageWhitelistListEmpty());
        sender.sendMessage(emptyMessage);
    }

    private void sendWhitelistInfoMessage(Audience sender, String playerList) {
        Map<String, String> infoPlaceholders = new HashMap<>();
        infoPlaceholders.put("whitelist_list", playerList);
        infoPlaceholders.put("whitelist_count", String.valueOf(playerCount)); // Convert playerCount to String
        Component whitelistInfo = messageUtil.create(configModel.getMessageWhitelistListInfo(), infoPlaceholders);
        sender.sendMessage(whitelistInfo);
    }

    private String getPlayerList(List<String> playerList) {
        playerCount = playerList.size();
        return playerList.stream()
                .map(playerName -> configModel.getMessageWhitelistListUser().replace("%player_name%", playerName))
                .collect(Collectors.joining(configModel.getMessageWhitelistListSeparator()));
    }

    public boolean execute(Audience sender) {
        List<UserModel> users = database.getUsers();

        if (users.isEmpty()) {
            sendEmptyWhitelistMessage(sender);
            return true;
        }

        List<String> whitelist = users.stream()
                .map(UserModel::getNickname)
                .collect(Collectors.toList());

        String playerList = getPlayerList(whitelist);
        sendWhitelistInfoMessage(sender, playerList);

        return true;
    }
}
