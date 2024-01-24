package me.truec0der.mwhitelist.database;

import me.truec0der.mwhitelist.models.UserModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Database {
    CompletableFuture<UserModel> getUser(String nickname);

    CompletableFuture<Void> createUser(String nickname);

    CompletableFuture<Void> deleteUser(String nickname);

    CompletableFuture<List<UserModel>> getUsers();

    CompletableFuture<Void> setUserTime(String nickname, long time);
}
