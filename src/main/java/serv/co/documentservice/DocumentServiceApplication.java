package serv.co.documentservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import serv.co.documentservice.connection.MongoConfig;
import serv.co.documentservice.connection.MyConfiguration;
import serv.co.documentservice.controller.DocController;
import serv.co.documentservice.repository.DocRepository;


@SpringBootApplication
@Import({MyConfiguration.class, MongoConfig.class})

public class DocumentServiceApplication {
   public static void main(String[] args) {
       SpringApplication.run(DocumentServiceApplication.class, args);
   }
    @Bean
   CommandLineRunner start(DocRepository repository ,DocController docController, MyConfiguration restConfiguration){
      return args -> {
           restConfiguration.repositoryRestConfigurer();
          System.out.println("Application started and running !!!!!!!!!!! ");
          docController.printAllDocs();
          System.out.println("Application started and running !!!!!!!!!!! ");
          System.out.println("Application started and running !!!!!!!!!!! ");
            docController.getDocx("test.png");
          docController.getDocInfoByName("test.png");


          /*LocalDateTime creationTime = LocalDateTime.parse("2023-06-09T15:25:54.384");
          docController.getDocsByCreationTime(creationTime);
          docController.getDocByDocuName("Documentccf164");*/



      };
    }
}