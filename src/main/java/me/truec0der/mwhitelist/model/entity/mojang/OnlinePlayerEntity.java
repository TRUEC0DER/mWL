package me.truec0der.mwhitelist.model.entity.mojang;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnlinePlayerEntity {
    @NonNull String id;
    @NonNull String nickname;

    public static OnlinePlayerEntity toEntity(JsonObject jsonObject) {
        if (!jsonObject.has("id") || !jsonObject.has("nickname")) return null;

        String uuid = jsonObject.get("id").getAsString();
        String nickname = jsonObject.get("nickname").getAsString();

        return OnlinePlayerEntity.builder()
                .id(uuid)
                .nickname(nickname)
                .build();
    }
}
