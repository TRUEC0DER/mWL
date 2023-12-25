package me.truec0der.mwhitelist.events;

import me.truec0der.mwhitelist.MWhitelist;
import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.mongodb.MongoDBUserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import org.bson.Document;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginEventListener implements Listener {
    private ConfigModel configModel;
    private MongoDBUserModel mongoDBUserModel;
    private MessageUtil messageUtil;

    public PlayerLoginEventListener(ConfigModel configModel, MongoDBUserModel mongoDBUserModel, MessageUtil messageUtil) {
        this.configModel = configModel;
        this.messageUtil = messageUtil;
        this.mongoDBUserModel = mongoDBUserModel;
    }
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();
        Document findPlayer = mongoDBUserModel.findByNickname(playerName);

        boolean whitelistStatus = configModel.getWhitelistStatus();

        if (findPlayer == null && whitelistStatus) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, messageUtil.create(configModel.getMessageWhitelistNotIn()));
        }
    }
}
