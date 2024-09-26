package me.truec0der.mwhitelist.interfaces.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import me.truec0der.mwhitelist.interfaces.repository.Repository;

public interface JsonRepository extends Repository {
    JsonArray getDatabase();

    Gson getGson();

    void save();
}
