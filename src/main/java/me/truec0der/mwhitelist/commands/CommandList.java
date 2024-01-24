package me.truec0der.mwhitelist.commands;

import me.truec0der.mwhitelist.database.Database;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import net.kyori.adventure.audience.Audience;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
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
        sender.sendMessage(messageUtil.create(configModel.getMessageWhitelistListEmpty()));
    }

    private void sendWhitelistInfoMessage(Audience sender, String playerList) {
        Map<String, String> infoPlaceholders = Map.of(
                "whitelist_list", playerList,
                "whitelist_count", String.valueOf(playerCount)
        );
        sender.sendMessage(messageUtil.create(configModel.getMessageWhitelistListInfo(), infoPlaceholders));
    }

    private String getPlayerList(List<String> playerList) {
        playerCount = playerList.size();
        String whitelistListUserMessage = configModel.getMessageWhitelistListUser();
        String separator = configModel.getMessageWhitelistListSeparator();

        if (whitelistListUserMessage != null && separator != null) {
            return playerList.stream()
                    .map(playerName -> whitelistListUserMessage.replace("%player_name%", playerName))
                    .collect(Collectors.joining(separator));
        }
        return whitelistListUserMessage;
    }

    public boolean execute(Audience sender) {
        CompletableFuture<List<UserModel>> usersFuture = database.getUsers();

        usersFuture.thenAcceptAsync(users -> {
            if (users.isEmpty()) {
                sendEmptyWhitelistMessage(sender);
            } else {
                List<String> whitelist = users.stream()
                        .map(UserModel::getNickname)
                        .collect(Collectors.toList());

                String playerList = getPlayerList(whitelist);
                sendWhitelistInfoMessage(sender, playerList);
            }
        });

        return true;
    }
}
