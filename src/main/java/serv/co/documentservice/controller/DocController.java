package serv.co.documentservice.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public String createDoc(@RequestParam("docx") MultipartFile file , @RequestParam("name") String docname , @RequestParam("description") String docdescrpt) throws IOException {

        Doc doc = Doc.builder()
                .name(docname)
                .type(file.getContentType())
                .description("Description" + docdescrpt)
                .build();
        doc.setDocx(DocBinaryUtility.compressImage(file.getBytes()));
        doc.setChecksum(file.getBytes());

        DocMetadata metadata = new DocMetadata();
        metadata.setSize(file.getSize());
        metadata.setDocuName(doc.getName());
        metadata.setCreationTime(LocalDateTime.now());
        metadata.setCreePar("unknown");
        metadata.setNatureDocu(doc.getType());

        doc.setMetadata(metadata);

        repository.save(doc);

        return "Document validated";
    }



    @GetMapping("/print")
     public void printAllDocs() {
         repository.findAll().forEach(System.out::println);
    }

   @GetMapping(path = {"/get/doc/infoJson/{name}"})
   public Optional<Doc> getDocInfoByName(@PathVariable("name") String name) throws IOException {
       final Optional<Doc> dbDoc = repository.findByName(name);

       return dbDoc.map(doc -> {
           byte[] imageBytes = DocBinaryUtility.decompressImage(doc.getDocx());

           String checksum = DigestUtils.sha256Hex(imageBytes);
           if (checksum.equals(doc.getChecksum())) {
               System.out.println("Checksum matched for document: " + name);
           } else {
               System.out.println("Checksum mismatch for document: " + name);
           }

           return Doc.builder()
                   .id(doc.getId())
                   .name(doc.getName())
                   .type(doc.getType())
                   .docx(imageBytes)
                   .checksum(doc.getChecksum())  // Include checksum field
                   .description(doc.getDescription())  // Include description field
                   .metadata(doc.getMetadata())  // Include metadata field
                   .build();
       });
   }

    @GetMapping("/get/docx")
    public ResponseEntity<List<Doc>> getAllDocx() {
        List<Doc> docList = repository.findAll();
        return ResponseEntity.ok().body(docList);
    }

    @GetMapping(path = {"/get/docx/{name}"})
    public ResponseEntity<byte[]> getDocx(@PathVariable("name") String name) throws IOException {
        final Optional<Doc> dbDoc = repository.findByName(name);

        if (dbDoc.isPresent()) {
            Doc doc = dbDoc.get();
            byte[] imageBytes = DocBinaryUtility.decompressImage(doc.getDocx());

            String checksum = DigestUtils.sha256Hex(imageBytes);
            if (checksum.equals(doc.getChecksum())) {
                System.out.println("Checksum matched for document: " + name);
            } else {
                System.out.println("Checksum mismatch for document: " + name);
            }

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(doc.getType()))
                    .body(imageBytes);
        }

        return ResponseEntity.notFound().build();
    }
    @GetMapping("/checksum/{name}")
    public ResponseEntity<String> getChecksumByName(@PathVariable("name") String name) {
        Optional<Doc> docOptional = repository.findByName(name);

        if (docOptional.isPresent()) {
            Doc doc = docOptional.get();
            return ResponseEntity.ok(doc.getChecksum());
        }

        return ResponseEntity.notFound().build();
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
