package me.truec0der.mwhitelist.models.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import me.truec0der.mwhitelist.MWhitelist;
import me.truec0der.mwhitelist.managers.MongoDBManager;
import me.truec0der.mwhitelist.models.ConfigModel;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDBUserModel {
    private MongoDBManager mongoDBManager;
    private MongoCollection<Document> userCollection;
    private ConfigModel configModel;

    public MongoDBUserModel(MongoDBManager mongoDBManager, ConfigModel configModel) {
        this.mongoDBManager = mongoDBManager;
        this.configModel = configModel;
        this.userCollection = mongoDBManager.getCollection(configModel.getMongoCollectionUser());
    }

    public InsertOneResult insertOne(String nickname) {
        Document userDocument = new Document()
                .append("nickname", nickname);

        return userCollection.insertOne(userDocument);
    }

    public FindIterable<Document> findAll() {
        return userCollection.find();
    }

    public Document findByNickname(String nickname) {
        Bson filter = Filters.eq("nickname", nickname);
        return userCollection.find(filter).first();
    }

    public DeleteResult deleteByNickname(String nickname) {
        Bson filter = Filters.eq("nickname", nickname);
        return userCollection.deleteOne(filter);
    }
}
