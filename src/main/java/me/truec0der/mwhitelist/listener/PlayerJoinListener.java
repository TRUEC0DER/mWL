package me.truec0der.mwhitelist.listener;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.service.ServiceRegister;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerJoinListener implements Listener {
    ServiceRegister serviceRegister;

    @EventHandler
    private void checkWhitelistOnJoin(PlayerLoginEvent event) {
        serviceRegister.getWhitelistActionService().handleJoin(event);
    }
}
