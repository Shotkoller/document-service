package serv.co.documentservice;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import serv.co.documentservice.connection.MongoConfig;
import serv.co.documentservice.connection.MyConfiguration;
import serv.co.documentservice.controller.DocController;
import serv.co.documentservice.repository.DocRepository;

import java.time.LocalDateTime;


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
          // You can add any additional initialization logic here
          /* docController.createDoc();
          Thread.sleep(5000); // Delay the execution for 5 seconds
          docController.createDoc();
          Thread.sleep(5000);
          docController.createDoc();*/


          docController.printAllDocs();
          System.out.println("Application started and running !!!!!!!!!!! ");
          System.out.println("Application started and running !!!!!!!!!!! ");



          LocalDateTime creationTime = LocalDateTime.parse("2023-06-09T15:25:54.384");
          docController.getDocsByCreationTime(creationTime);
          docController.getDocByDocuName("Documentccf164");



      };
    }
}