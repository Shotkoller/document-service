package serv.co.documentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import serv.co.documentservice.repository.DocRepository;
import serv.co.documentservice.model.Doc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/docudb")
public class DocController {
    private final DocRepository repository;

    public DocController(DocRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Doc creatDoc(Doc doc){
        return repository.save(doc);
    }
    @GetMapping
     public void printAllDocs() {
         repository.findAll().forEach(System.out::println);
    }
    @GetMapping
    public List<Doc> getAllDoc(){
        return repository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Doc> getDocId(@PathVariable String id){
        return repository.findById(id);
    }

    @GetMapping("/{name}")
    public List<Doc> getDocName(@PathVariable String name){
        return repository.getDocByName(name);
    }

    /*@PutMapping
    public Docum modifyDoc(Docum doc){
        return repository.save(doc);
    }*/

    @DeleteMapping("/{id}")
    public String deleteDocId(@PathVariable String id){
        repository.deleteById(id);
        return "deleted doc id" + id;
    }

    @PutMapping
    public Doc createRandomDoc(){
        Doc doc=new Doc().builder()
                 //.id("DocId"+generateRandomString())
                .name("Document"+generateRandomString())
                .docx("Docx"+generateRandomString())
                .description("Description"+generateRandomString())
                .build();
        doc.setChecksum(doc.getDocx());
        return doc ;
    }
    private String generateRandomString(){  return UUID.randomUUID().toString() ;    }

}
