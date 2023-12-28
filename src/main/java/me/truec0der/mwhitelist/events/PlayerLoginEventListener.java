package me.truec0der.mwhitelist.events;

import me.truec0der.mwhitelist.database.Database;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

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
    public void onPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();
        UserModel findPlayer = database.getUser(playerName);

        boolean whitelistStatus = configModel.getWhitelistStatus();

        if (findPlayer == null && whitelistStatus) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, messageUtil.create(configModel.getMessageWhitelistNotIn()));
        }
    }
}
