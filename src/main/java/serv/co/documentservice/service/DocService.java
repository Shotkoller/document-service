package serv.co.documentservice.service;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import serv.co.documentservice.model.Doc;
import serv.co.documentservice.repository.DocRepository;

import java.util.List;

@Service

public class DocService {
    private serv.co.documentservice.repository.DocRepository repository ;
    private MongoTemplate mongoTemplate;


    // Constructor injection
    public void DocumentService(DocRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }
    public Doc addDoc(Doc doc){
        repository.save(doc);
        return doc;
    }
    public List<Doc> findAllDocs(){
        return   repository.findAll();
    }
    public Doc getDocById(String id){
        return repository.findById(id).get();
    }
    public List<Doc> getDocByName(String name){
        return repository.getDocByName(name);
    }
      public Doc updateDoc(Doc docRequest){
          Doc existingDoc = repository.findById(docRequest.getId()).get();
          existingDoc.setName(docRequest.getName());
          existingDoc.setDescription(docRequest.getDescription());

          return repository.save(existingDoc) ;

      }
    public String deleteDocById (String id){
        repository.deleteById(id);
        return id+"was deleted";
    }


};
