package serv.co.documentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import serv.co.documentservice.model.Doc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@EnableMongoRepositories


public interface DocRepository extends MongoRepository<Doc,String> {

    List<Doc> getDocByName(String name);


    Optional<Doc> findByMetadataCreationTime(LocalDateTime creationTime);

    Optional<Doc> findByMetadataDocuName(String docuName);
}