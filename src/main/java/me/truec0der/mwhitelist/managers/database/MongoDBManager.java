package me.truec0der.mwhitelist.managers.database;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBManager {
    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;

    public MongoDBManager(String connectionString, String databaseName) {
        this.mongoClient = MongoClients.create(connectionString);
        this.mongoDatabase = mongoClient.getDatabase(databaseName);
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        try {
            return mongoDatabase.getCollection(collectionName);
        } catch (MongoCommandException e) {
            mongoDatabase.createCollection(collectionName);
            return mongoDatabase.getCollection(collectionName);
        }
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
