package me.truec0der.mwhitelist.events;

import me.truec0der.mwhitelist.database.Database;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.concurrent.CompletableFuture;

public class PlayerLoginEventListener implements Listener {
    private ConfigModel configModel;
    private Database database;
    private MessageUtil messageUtil;

    public PlayerLoginEventListener(ConfigModel configModel, Database database, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.database = database;
    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        if (!configModel.getWhitelistStatus()) return;

        String playerName = event.getName();

        CompletableFuture<UserModel> findPlayer = database.getUser(playerName);

        UserModel player = findPlayer.join();
        ;

        long currentTimeMillis = System.currentTimeMillis();

        if (player != null && player.getTime() > 0 && player.getTime() < currentTimeMillis) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, messageUtil.create(configModel.getMessageWhitelistNotIn()));
            database.deleteUser(playerName);
        } else if (player == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, messageUtil.create(configModel.getMessageWhitelistNotIn()));
        }
    }
}
