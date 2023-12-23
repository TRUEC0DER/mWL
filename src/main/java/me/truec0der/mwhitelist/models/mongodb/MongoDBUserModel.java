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

    private static MongoDBManager mongoDBManager = MWhitelist.getMongoDBManager();
    private static MongoCollection<Document> userCollection = mongoDBManager.getCollection(ConfigModel.getMongoCollectionUser());


    public static InsertOneResult insertOne(String nickname) {
        Document userDocument = new Document()
                .append("nickname", nickname);

        return userCollection.insertOne(userDocument);
    }

    public static FindIterable<Document> findAll() {
        return userCollection.find();
    }

    public static Document findByNickname(String nickname) {
        Bson filter = Filters.eq("nickname", nickname);
        return userCollection.find(filter).first();
    }

    public static DeleteResult deleteByNickname(String nickname) {
        Bson filter = Filters.eq("nickname", nickname);
        return userCollection.deleteOne(filter);
    }
}
