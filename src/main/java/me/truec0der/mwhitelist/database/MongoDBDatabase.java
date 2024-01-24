package me.truec0der.mwhitelist.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import me.truec0der.mwhitelist.managers.database.MongoDBManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

public class MongoDBDatabase implements Database {
    private MongoDBManager mongoDBManager;
    private MongoCollection<Document> userCollection;
    private ConfigModel configModel;

    public MongoDBDatabase(MongoDBManager mongoDBManager, ConfigModel configModel) {
        this.mongoDBManager = mongoDBManager;
        this.configModel = configModel;
        this.userCollection = this.mongoDBManager.getCollection(this.configModel.getMongoCollectionUser());
    }

    @Override
    public CompletableFuture<Void> createUser(String nickname) {
        return CompletableFuture.runAsync(() -> {
            Document userDocument = new Document()
                    .append("nickname", nickname)
                    .append("temp", 0L);

            userCollection.insertOne(userDocument);
        });
    }

    @Override
    public CompletableFuture<Void> deleteUser(String nickname) {
        return CompletableFuture.runAsync(() -> {
            if (configModel.getIsSensitiveCase()) {
                deleteUserSensitive(nickname);
            } else {
                deleteUserInsensitive(nickname);
            }
        });
    }

    private CompletableFuture<Void> deleteUserSensitive(String nickname) {
        return CompletableFuture.runAsync(() -> {
            Document query = new Document("nickname", nickname);
            userCollection.deleteOne(query);
        });
    }

    private CompletableFuture<Void> deleteUserInsensitive(String nickname) {
        return CompletableFuture.runAsync(() -> {
            Document query = new Document("nickname", new Document("$regex", nickname).append("$options", "i"));
            userCollection.deleteOne(query);
        });
    }

    @Override
    public CompletableFuture<List<UserModel>> getUsers() {
        return CompletableFuture.supplyAsync(() -> {
            List<UserModel> users = new ArrayList<>();
            FindIterable<Document> findUsers = userCollection.find();

            for (Document userDocument : findUsers) {
                String nickname = userDocument.getString("nickname");
                UserModel user = new UserModel(nickname);
                users.add(user);
            }

            return users;
        });
    }

    @Override
    public CompletableFuture<UserModel> getUser(String nickname) {
        return configModel.getIsSensitiveCase() ? getUserSensitive(nickname) : getUserInsensitive(nickname);
    }

    @Override
    public CompletableFuture<Void> setUserTime(String nickname, long time) {
        return CompletableFuture.supplyAsync(() -> {
            Bson filter = Filters.eq("nickname", nickname);
            Document update = new Document("$set", new Document("temp", time));

            userCollection.updateOne(filter, update);

            UserModel userModel = new UserModel(nickname);
            userModel.setTime(time);

            return null;
        });
    }

    private CompletableFuture<UserModel> getUserSensitive(String nickname) {
        return CompletableFuture.supplyAsync(() -> {
            Bson filter = Filters.eq("nickname", nickname);
            Document findUser = userCollection.find(filter).first();

            if (findUser == null) return null;

            UserModel userModel = new UserModel(findUser.getString("nickname"));

            Long tempValue = findUser.getLong("temp");

            userModel.setTime(tempValue);

            return userModel;
        });
    }

    private CompletableFuture<UserModel> getUserInsensitive(String nickname) {
        return CompletableFuture.supplyAsync(() -> {
            Bson filter = Filters.regex("nickname", Pattern.compile("^" + nickname + "$", Pattern.CASE_INSENSITIVE));
            Document findUser = userCollection.find(filter).first();

            if (findUser == null) return null;

            UserModel userModel = new UserModel(findUser.getString("nickname"));

            Long tempValue = findUser.getLong("temp");

            userModel.setTime(tempValue);

            return userModel;
        });
    }
}

