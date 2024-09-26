package me.truec0der.mwhitelist.model.entity.plugin;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PluginVersionEntity {
    String version;
    String url;
    List<String> info;

    public static PluginVersionEntity toEntity(JSONObject jsonObject) {
        JSONArray infoArray = (JSONArray) jsonObject.get("info");
        List<String> infoList = new ArrayList<>();

        infoArray.forEach(value -> {
            infoList.add(value.toString());
        });

        return PluginVersionEntity.builder()
                .version(jsonObject.get("version").toString())
                .url(jsonObject.get("url").toString())
                .info(infoList)
                .build();
    }
}
