package me.truec0der.mwhitelist.database;

import me.truec0der.mwhitelist.managers.database.YamlDBManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class YamlDatabase implements Database {
    private YamlDBManager yamlDBManager;
    private ConfigModel configModel;

    public YamlDatabase(YamlDBManager yamlDBManager, ConfigModel configModel) {
        this.yamlDBManager = yamlDBManager;
        this.configModel = configModel;
    }

    @Override
    public UserModel getUser(String nickname) {
        UserModel findUser;
        if (configModel.getIsSensitiveCase()) {
            findUser = getUserSensitive(nickname);
        } else {
            findUser = getUserInsensitive(nickname);
        }
        return findUser;
    }

    ;

    public UserModel getUserSensitive(String nickname) {
        List<String> findUsers = yamlDBManager.getYamlDatabase().getStringList("whitelist");
        if (findUsers.contains(nickname)) {
            return new UserModel(nickname);
        }
        return null;
    }

    private UserModel getUserInsensitive(String nickname) {
        List<String> findUsers = yamlDBManager.getYamlDatabase().getStringList("whitelist");
        for (String user : findUsers) {
            if (user.equalsIgnoreCase(nickname)) {
                return new UserModel(user);
            }
        }
        return null;
    }

    @Override
    public UserModel createUser(String nickname) {
        List<String> findUsers = yamlDBManager.getYamlDatabase().getStringList("whitelist");
        findUsers.add(nickname);
        yamlDBManager.getYamlDatabase().set("whitelist", findUsers);
        yamlDBManager.save();
        return new UserModel(nickname);
    }

    @Override
    public UserModel deleteUser(String nickname) {
        List<String> findUsers = yamlDBManager.getYamlDatabase().getStringList("whitelist");
        findUsers.remove(nickname);
        yamlDBManager.getYamlDatabase().set("whitelist", findUsers);
        yamlDBManager.save();
        return new UserModel(nickname);
    }

    @Override
    public List<UserModel> getUsers() {
        List<UserModel> users = new ArrayList<>();
        List<String> findUsers = yamlDBManager.getYamlDatabase().getStringList("whitelist");

        for (String userNickname : findUsers) {
            UserModel user = new UserModel(userNickname);
            users.add(user);
        }

        return users;
    }
}
