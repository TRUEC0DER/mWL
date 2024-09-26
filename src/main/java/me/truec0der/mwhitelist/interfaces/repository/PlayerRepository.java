package me.truec0der.mwhitelist.interfaces.repository;

import me.truec0der.mwhitelist.model.entity.database.PlayerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
    List<PlayerEntity> find();

    Optional<PlayerEntity> find(UUID uuid, boolean isOnline);

    boolean isExists(UUID uuid, boolean isOnline);

    void create(String nickname, UUID offlineUuid, UUID onlineUuid);

    void remove(UUID uuid, boolean isOnline);

    void setTime(UUID uuid, boolean isOnline, long time);
}
