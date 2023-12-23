package me.truec0der.mwhitelist.events;

import me.truec0der.mwhitelist.managers.ConfigManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.mongodb.MongoDBUserModel;
import me.truec0der.mwhitelist.utils.MessageUtil;
import org.bson.Document;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginEventListener implements Listener {
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();
        Document findPlayer = MongoDBUserModel.findByNickname(playerName);

        boolean whitelistStatus = ConfigManager.getConfig().getBoolean("whitelist.status");

        if (findPlayer == null && whitelistStatus) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, MessageUtil.createWithoutPrefix(ConfigModel.getMessageWhitelistNotIn()));
        }
    }
}
