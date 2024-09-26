package me.truec0der.mwhitelist.impl.repository.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.truec0der.mwhitelist.interfaces.repository.mongo.MongoRepository;
import org.bson.Document;

public class MongoRepositoryImpl implements MongoRepository {
    private final ConnectionString connectionString;
    private final String databaseName;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public MongoRepositoryImpl(ConnectionString connectionString, String databaseName) {
        this.connectionString = connectionString;
        this.databaseName = databaseName;
    }

    @Override
    public void init() {
        mongoClient = MongoClients.create(connectionString);
        mongoDatabase = mongoClient.getDatabase(databaseName);
    }

    @Override
    public MongoCollection<Document> getCollection(String collectionName) {
        try {
            return mongoDatabase.getCollection(collectionName);
        } catch (MongoCommandException e) {
            mongoDatabase.createCollection(collectionName);
            return mongoDatabase.getCollection(collectionName);
        }
    }

    @Override
    public void closeConnection() {
        if (mongoClient != null) mongoClient.close();
    }
}
