package me.truec0der.mwhitelist.impl.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Getter;
import me.truec0der.mwhitelist.interfaces.repository.json.JsonRepository;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonRepositoryImpl implements JsonRepository {
    private final Plugin plugin;
    private final File filePath;
    private final String file;
    @Getter
    private final Gson gson;
    private JsonArray jsonDatabase;

    public JsonRepositoryImpl(Plugin plugin, File filePath, String file) {
        this.plugin = plugin;
        this.filePath = filePath;
        this.file = file;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void init() {
        if (!filePath.exists()) {
            plugin.saveResource(file, false);
        }

        try (FileReader reader = new FileReader(new File(filePath, ""))) {
            JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
            this.jsonDatabase = jsonElement.isJsonArray() ? jsonElement.getAsJsonArray() : new JsonArray();
        } catch (IOException e) {
            e.printStackTrace();
            this.jsonDatabase = new JsonArray();
        }
    }

    public JsonArray getDatabase() {
        return this.jsonDatabase;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(new File(filePath, ""))) {
            gson.toJson(jsonDatabase, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}