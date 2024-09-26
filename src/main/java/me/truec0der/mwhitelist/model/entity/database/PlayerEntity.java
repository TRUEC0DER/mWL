package me.truec0der.mwhitelist.model.entity.database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerEntity {
    @Nullable PlayerUuid uuid;
    @Nullable PlayerInfo info;
    @Nullable Long time;

    public static PlayerEntity toEntity(JsonObject jsonObject) {
        Gson gson = new Gson();

        if (!jsonObject.has("uuid") || !jsonObject.has("info")) return null;

        JsonObject uuid = jsonObject.getAsJsonObject("uuid");
        JsonObject info = jsonObject.getAsJsonObject("info");

        UUID offlineUuid = UUID.fromString(uuid.get("offline").getAsString());
        UUID onlineUuid = UUID.fromString(uuid.get("online").getAsString());

        List<String> nicknameHistory = Arrays.asList(gson.fromJson(info.get("nicknameHistory").getAsJsonArray(), String[].class));
        long lastUpdate = info.get("lastUpdate").getAsLong();

        Long time = jsonObject.has("time") ? jsonObject.get("time").getAsLong() : -1;

        return PlayerEntity.builder()
                .uuid(new PlayerUuid(offlineUuid, onlineUuid))
                .info(new PlayerInfo(nicknameHistory, lastUpdate))
                .time(time)
                .build();
    }

    public boolean isTimeExists(long currentTime) {
        return time >= 0 && time < currentTime;
    }

    public Long getEstimatedTime() {
        return time - new Date().getTime();
    }

    public boolean isTimeExpired() {
        return time > 0 && new Date().getTime() > time;
    }

    public boolean isTimeInfinity() {
        return time < 0;
    }

    public String formatTime(SimpleDateFormat format) {
        return format.format(new Date(time));
    }

    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @Getter
    @Setter
    public static class PlayerUuid {
        UUID offline;
        UUID online;
    }

    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @Getter
    @Setter
    public static class PlayerInfo {
        List<String> nicknameHistory;
        Long lastUpdate;
    }
}
