package me.truec0der.mwhitelist.database;

import me.truec0der.mwhitelist.managers.database.YamlDBManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class YamlDatabase implements Database {
    private YamlDBManager yamlDBManager;
    private ConfigModel configModel;
    private YamlConfiguration yamlDB;

    public YamlDatabase(YamlDBManager yamlDBManager, ConfigModel configModel) {
        this.yamlDBManager = yamlDBManager;
        this.configModel = configModel;
        this.yamlDB = yamlDBManager.getYamlDatabase();

        migrateFormat();
    }

    @Override
    public CompletableFuture<UserModel> getUser(String nickname) {
        return configModel.getIsSensitiveCase() ? getUserSensitive(nickname) : getUserInsensitive(nickname);
    }

    @Override
    public CompletableFuture<Void> createUser(String nickname) {
        return CompletableFuture.runAsync(() -> {
            ConfigurationSection whitelistSection = yamlDB.getConfigurationSection("whitelist");

            if (whitelistSection == null) {
                whitelistSection = yamlDB.createSection("whitelist");
            }

            whitelistSection.createSection(nickname);
            setUserTime(nickname, 0);

            yamlDBManager.save();
        });
    }

    @Override
    public CompletableFuture<Void> deleteUser(String nickname) {
        if (configModel.getIsSensitiveCase()) {
            deleteUserSensitive(nickname);
        } else {
            deleteUserInsensitive(nickname);
        }

        return null;
    }

    private CompletableFuture<Void> deleteUserInsensitive(String nickname) {
        return CompletableFuture.runAsync(() -> {
            Set<String> findUsers = yamlDB.getConfigurationSection("whitelist").getKeys(false);
            for (String user : findUsers) {
                if (user.equalsIgnoreCase(nickname)) {
                    yamlDB.set("whitelist." + user, null);
                    yamlDBManager.save();
                }
            }
        });
    }

    public CompletableFuture<Void> deleteUserSensitive(String nickname) {
        return CompletableFuture.runAsync(() -> {
            ConfigurationSection whitelistSection = yamlDBManager.getYamlDatabase().getConfigurationSection("whitelist");

            if (whitelistSection != null) {
                whitelistSection.set(nickname, null);
                yamlDBManager.save();
            }
        });
    }

    @Override
    public CompletableFuture<List<UserModel>> getUsers() {
        return CompletableFuture.supplyAsync(() -> {
            List<UserModel> usersList = new ArrayList<>();
            ConfigurationSection whitelistSection = yamlDB.getConfigurationSection("whitelist");

            if (whitelistSection != null) {
                for (String userNickname : whitelistSection.getKeys(false)) {
                    UserModel user = new UserModel(userNickname);
                    usersList.add(user);
                }
            }

            return usersList;
        });
    }

    @Override
    public CompletableFuture<Void> setUserTime(String nickname, long time) {
        return CompletableFuture.runAsync(() -> {
            yamlDB.set("whitelist." + nickname + ".temp", time);
            yamlDBManager.save();
        });
    }

    public CompletableFuture<UserModel> getUserSensitive(String nickname) {
        return CompletableFuture.supplyAsync(() -> {
            ConfigurationSection whitelistSection = yamlDB.getConfigurationSection("whitelist");

            if (whitelistSection != null && whitelistSection.getKeys(false).contains(nickname)) {
                UserModel userModel = new UserModel(nickname);
                userModel.setTime(whitelistSection.getLong(nickname + ".temp"));
                return userModel;
            } else {
                return null;
            }
        });
    }

    public CompletableFuture<UserModel> getUserInsensitive(String nickname) {
        return CompletableFuture.supplyAsync(() -> {
            ConfigurationSection whitelistSection = yamlDB.getConfigurationSection("whitelist");

            if (whitelistSection != null) {
                String matchingNickname = whitelistSection.getKeys(false).stream()
                        .filter(key -> key.equalsIgnoreCase(nickname))
                        .findFirst()
                        .orElse(null);

                if (matchingNickname != null) {
                    UserModel userModel = new UserModel(matchingNickname);
                    userModel.setTime(whitelistSection.getLong(matchingNickname + ".temp"));
                    return userModel;
                }
            }

            return null;
        });
    }

    private void migrateFormat() {
        if (!yamlDB.isList("whitelist")) return;

        List<String> whitelist = yamlDB.getStringList("whitelist");

        for (String player : whitelist) {
            yamlDB.set("whitelist." + player + ".temp", 0);
        }

        yamlDBManager.save();
    }
}
