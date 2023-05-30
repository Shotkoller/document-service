package serv.co.documentservice.service;


import org.springframework.stereotype.Service;
import serv.co.documentservice.model.Doc;
import serv.co.documentservice.repository.DocRepository;

import java.util.List;

@Service

public class DocService {
    private serv.co.documentservice.repository.DocRepository repository ;

    // Constructor injection
    public DocService(DocRepository docRepository) {
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
    /*  public Docum updateDoc(Docum docRequest){
          Docum existingDoc = repository.findById(docRequest.getId()).get();
          existingDoc.setName(docRequest.getName());
          existingDoc.setDescription(docRequest.getDescription());
          existingDoc.setDocx(docRequest.getDocx());
          existingDoc.setChecksum(docRequest.getDocx());
          return repository.save(existingDoc) ;

      }*/
    public String deleteDocById (String id){
        repository.deleteById(id);
        return id+"was deleted";
    }

};
