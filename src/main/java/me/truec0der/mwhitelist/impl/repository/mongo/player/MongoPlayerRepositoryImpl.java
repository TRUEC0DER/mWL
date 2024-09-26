package me.truec0der.mwhitelist.impl.repository.mongo.player;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.mwhitelist.interfaces.repository.PlayerRepository;
import me.truec0der.mwhitelist.interfaces.repository.mongo.MongoRepository;
import me.truec0der.mwhitelist.model.entity.database.PlayerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MongoPlayerRepositoryImpl implements PlayerRepository {
    MongoRepository mongoRepository;

    @Override
    public Optional<PlayerEntity> find(UUID uuid, boolean isOnline) {
        return null;
    }

    @Override
    public boolean isExists(UUID uuid, boolean isOnline) {
        return false;
    }

    @Override
    public void create(String nickname, UUID offlineUuid, UUID onlineUuid) {

    }

    @Override
    public void remove(UUID uuid, boolean isOnline) {

    }

    @Override
    public List<PlayerEntity> find() {
        return null;
    }

    @Override
    public void setTime(UUID uuid, boolean isOnline, long time) {

    }
}
