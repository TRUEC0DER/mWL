package me.truec0der.mwhitelist.interfaces.repository.mongo;

import com.mongodb.client.MongoCollection;
import me.truec0der.mwhitelist.interfaces.repository.Repository;
import org.bson.Document;

public interface MongoRepository extends Repository {
    MongoCollection<Document> getCollection(String collectionName);

    void closeConnection();
}
