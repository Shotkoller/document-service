package serv.co.documentservice.controller;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import serv.co.documentservice.model.Doc;
import serv.co.documentservice.model.DocMetadata;
import serv.co.documentservice.repository.DocRepository;
import serv.co.documentservice.util.DocBinaryUtility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/docudb")
public class DocController {
    private final DocRepository repository;
    private final MongoTemplate mongoTemplate;
    private RestTemplate restTemplate;
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public DocController(DocRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping("/create/doc")
    @ResponseStatus(HttpStatus.CREATED)
    public String createDoc(@RequestParam("docx") MultipartFile file) throws IOException {

        Doc doc = Doc.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .description("Description" + generateRandomString())
                .build();
        doc.setDocx(DocBinaryUtility.compressImage(file.getBytes()));
        doc.setChecksum(file.getBytes());

        DocMetadata metadata = new DocMetadata();
        metadata.setDocuName(doc.getName());
        metadata.setCreationTime(LocalDateTime.now());

        doc.setMetadata(metadata);

        repository.save(doc);

        return "Document validated";
    }



    @GetMapping("/print")
     public void printAllDocs() {
         repository.findAll().forEach(System.out::println);
    }


    @GetMapping("/{id}")
    public Optional<Doc> getDocId(@PathVariable String id){
        return repository.findById(id);
    }

   /* @GetMapping
    public List<Doc> getAllDoc(){
        return repository.findAll();
    }
    @GetMapping("/{name}")
    public List<Doc> getDocName(@PathVariable String name){
        return repository.getDocByName(name);
    }*/
   @GetMapping("/all")
   public List<Doc> getAllDocuments() {

       return repository.findAll();
   }
    @GetMapping("/DocMetadata/{creationTime}")
    public void getDocsByCreationTime(@RequestParam("creationTime") LocalDateTime creationTime) {
        Optional<Doc> docOptional = repository.findByMetadataCreationTime(creationTime);

        if (docOptional.isPresent()) {
            Doc doc = docOptional.get();
            // Print the doc details
            System.out.println("Doc found with creationTime: " + creationTime);
            System.out.println(doc.toString());        }
        else {
            System.out.println("No Doc found with creationTime: " + creationTime);
        }
    }
    @GetMapping("/DocMetadata/{docuName}")
    public void getDocByDocuName(@PathVariable("docuName") String docuName) {
        Optional<Doc> docOptional = repository.findByMetadataDocuName(docuName);

        if (docOptional.isPresent()) {
            Doc doc = docOptional.get();
            System.out.println("Doc found with docuName: " + docuName);
            System.out.println(doc.toString());
        } else {
            System.out.println("No Doc found with docuName: " + docuName);
        }
    }


    @PutMapping
    public Doc modifyDoc(Doc doc){
        return repository.save(doc);

    }

    @DeleteMapping("/{id}")
    public String deleteDocId(@PathVariable String id){
        repository.deleteById(id);
        return "deleted doc id" + id;
    }


    private String generateRandomString() {
        String randomString = UUID.randomUUID().toString();
        // Extract the first 5 characters from the random string
        return randomString.substring(0, 6);
    }


}
