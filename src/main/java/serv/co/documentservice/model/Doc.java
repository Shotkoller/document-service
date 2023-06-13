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
    private byte[] docx;
    private String type;
    private String checksum;


    public byte[] getDocx() {
        return docx;
    }

    public void setImage(byte[] docx) {
        this.docx = docx;
    }


    public void setChecksum(byte[] docxo) {
        this.checksum = DigestUtils.sha256Hex(docxo);
}





}
