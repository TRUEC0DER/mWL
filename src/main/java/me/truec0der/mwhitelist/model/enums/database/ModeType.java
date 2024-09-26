package me.truec0der.mwhitelist.model.enums.database;

import lombok.Getter;

@Getter
public enum ModeType {
    OFFLINE(false),
    ONLINE(true);

    private final boolean online;

    ModeType(boolean online) {
        this.online = online;
    }
}
