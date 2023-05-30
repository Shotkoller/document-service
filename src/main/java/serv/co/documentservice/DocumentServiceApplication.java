package serv.co.documentservice;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import serv.co.documentservice.controller.DocController;
import serv.co.documentservice.model.Doc;
import serv.co.documentservice.repository.DocRepository;


@SpringBootApplication
public class DocumentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentServiceApplication.class, args);
    }
    @Configuration
    public class MyConfiguration implements RepositoryRestConfigurer {

    }

    @Autowired
    public void setRepository(DocRepository repository) {
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
    }



    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Doc.class);
            // Additional configuration if needed
        });
    }
    @Bean
    public MongoClient mongoClient() {
        String connectionString = "mongodb://192.168.11.125:27017/ged";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase("docudb");
    }

    @Bean
    public CommandLineRunner start(DocController docController) {
        return args -> {
            docController.creatDoc(docController.createRandomDoc());
            docController.printAllDocs();
        };
    }

}
