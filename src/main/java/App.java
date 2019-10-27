import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

public class App {

    public static void main(String[] args) {
        Path path = Paths.get("file.txt");
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(path.toFile().getAbsoluteFile());
        System.getProperties().list(System.out);
        try {
            Files.lines(path).forEach(s -> stringBuilder.append(s));
            System.out.println(stringBuilder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = new Connection();) {

            MongoDatabase database = connection.getConnection();

//            connection.createCollection("people");

            MongoCollection peopleCollection = database.getCollection("people");

//            createNewDoc(peopleCollection, "Junior",33);
//            createNewDoc(peopleCollection, "Carvalho", 25);

            Consumer<Document> consumer = new Consumer<Document>() {
                @Override
                public void accept(final Document document) {
                    System.out.println(document.toJson());
                    try {
                        Person person = new ObjectMapper().readValue(document.toJson(), Person.class);
                        //           System.out.println(person.getAge());
                        //          System.out.println(document.getInteger("age"));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
//            Deprecated
//            Block showBlock = new Block() {
//                @Override
//                public void apply(final Object o) {
//                    System.out.println(o);
//                }
//            };

//            peopleCollection.find().forEach(consumer);
//           findAll(peopleCollection).forEach(consumer);
//            findBy("name", "Evandro", peopleCollection).forEach(consumer);
//            findBy("_id", new ObjectId("5db3c568c8c6f515fac5ecaf"), peopleCollection).forEach(consumer);
//            deleteOne("name", "Carvalho", peopleCollection);

            updateOne("5db3c568c8c6f515fac5ecaf", new Document("document", "123456"), peopleCollection);

        } catch (MongoException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createNewDoc(MongoCollection collection, String name, int age) {
        Person person = new Person(name, age);
        Document document = new Document();

        document.append("name", person.getName())
                .append("age", person.getAge());

        collection.insertOne(document);
    }

    private static FindIterable<Document> findAll(MongoCollection collection) {
        return collection.find();
    }

    private static FindIterable<Document> findBy(String key, Object value, MongoCollection collection) {
        final FindIterable findIterable = collection.find(eq(key, value));
        return findIterable;
    }

    private static DeleteResult deleteOne(String key, Object value, MongoCollection collection) {
        return collection.deleteOne(eq(key, value));
    }

    private static void updateOne(String id, Document updateValue, MongoCollection collection) {
        final FindIterable<Document> response = findBy("_id", new ObjectId(id), collection);
        for (Document document : response) {
            collection.updateOne(document, new Document("$set", updateValue));
        }
    }
}


