package serv.co.documentservice.model;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("docudb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Doc {

    @Id
    private String id;

    @Indexed(name = "indxnm")
    private String name;
    private String description;
    private DocMetadata metadata;
    private String docx;
    private String checksum;


    private Image image;

    public void setChecksum(String docx) {
        this.checksum = DigestUtils.sha256Hex(docx);
}





}
