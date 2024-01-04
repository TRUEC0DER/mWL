package me.truec0der.mwhitelist.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import me.truec0der.mwhitelist.managers.database.MongoDBManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import me.truec0der.mwhitelist.models.UserModel;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
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
    public UserModel createUser(String nickname) {
        Document userDocument = new Document()
                .append("nickname", nickname);

        InsertOneResult user = userCollection.insertOne(userDocument);

        return new UserModel(nickname);
    }

    @Override
    public UserModel deleteUser(String nickname) {
        Bson filter = Filters.eq("nickname", nickname);
        DeleteResult deleteUser = userCollection.deleteOne(filter);
        return new UserModel(nickname);
    }

    @Override
    public List<UserModel> getUsers() {
        List<UserModel> users = new ArrayList<>();
        FindIterable<Document> findUsers = userCollection.find();

        for (Document userDocument : findUsers) {
            String nickname = userDocument.getString("nickname");
            UserModel user = new UserModel(nickname);
            users.add(user);
        }

        return users;
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

    private UserModel getUserSensitive(String nickname) {
        Bson filter = Filters.eq("nickname", nickname);
        Document findUser = userCollection.find(filter).first();
        if (findUser == null) return null;
        return new UserModel(findUser.getString("nickname"));
    }

    private UserModel getUserInsensitive(String nickname) {
        Bson filter = Filters.regex("nickname", Pattern.compile("^" + nickname + "$", Pattern.CASE_INSENSITIVE));
        Document findUser = userCollection.find(filter).first();
        if (findUser == null) return null;
        return new UserModel(findUser.getString("nickname"));
    }
}
