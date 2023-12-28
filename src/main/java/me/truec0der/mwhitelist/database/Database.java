package me.truec0der.mwhitelist.database;

import me.truec0der.mwhitelist.models.UserModel;

import java.util.List;

public interface Database {
    UserModel getUser(String nickname);

    UserModel createUser(String nickname);

    UserModel deleteUser(String nickname);

    List<UserModel> getUsers();
}
