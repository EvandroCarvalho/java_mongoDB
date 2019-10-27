import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.io.Closeable;

public class Connection implements Closeable {
    private static final String HOST = "localhost";
    private static final int PORT = 2701;
    private static final String USERNAME = "";
    private static final String DATABASE = "";
    private static final char[] PASSWORD = null;

    private static MongoDatabase mongoDatabase = null;
    private static MongoClient mongoClient = null;


    public final MongoDatabase getConnection() {
        mongoClient = new MongoClient();
        if (mongoDatabase == null) {
            mongoDatabase = mongoClient.getDatabase("java_mongo");
        }
        return mongoDatabase;
    }

    public final void close() {
        mongoClient.close();
    }

//    public static void main(String... args) {
//        CREDENTIAL CONNECTION
//        final MongoCredential mongoCredential = MongoCredential.createCredential(USERNAME, DATABASE, PASSWORD);
//        final List credentials = Collections.unmodifiableList(Arrays.asList(mongoCredential));
//        final ServerAddress serverAddress = new ServerAddress(HOST, PORT);
//        final MongoClientOptions  mongoClientOptions = null;
//
//        final MongoClient mongoClient = new MongoClient(serverAddress, credentials, mongoClientOptions);
//        DB db = (DB) mongoClient.getDatabase("java_mongodb");

    //MongoClient mongoClient = new MongoClient();
    //MongoClient mongoClient = new MongoClient(HOST, PORT);

    //URI CONNECTION
//        final MongoClientURI uri = new MongoClientURI("mongodb://user:paasword@localhost/?authSource=db2&ssl=true");
//        MongoClient mongoClient = new MongoClient(uri);

//        try (MongoClient mongoClient = new MongoClient();) {
//            MongoDatabase database = mongoClient.getDatabase("java_mongo");
//            MongoCollection<Document> collection = database.getCollection("people");
//            System.out.println(collection.getNamespace());
//            //cria a coleção
//            collection.insertOne(new Document("key","value"));
//            //database.createCollection("people");
//            //captura a coleção para utiliza-lá
//            //MongoCollection<Document> collection = database.getCollection("people");
//            for (String name : database.listCollectionNames()) {
//                System.out.println(name);
//            }

    //    database.getCollection("people").drop();
//        }
//    }
}
