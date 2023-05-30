package serv.co.documentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import serv.co.documentservice.model.Doc;

import java.util.List;


@EnableMongoRepositories


public interface DocRepository extends MongoRepository<Doc,String> {

    List<Doc> getDocByName(String name);

}