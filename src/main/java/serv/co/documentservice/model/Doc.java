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
    /*@Id
    @org.springframework.data.mongodb.core.mapping.Field("_id")
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Converter to generate default value for _id field
    @ReadingConverter
    public static class IdConverter implements Converter<String, String> {
        @Override
        public String convert(String source) {
            if (source == null || source.isEmpty()) {
                // Generate default value here (e.g., UUID)
                return generateDefaultId();
            }
            return source;
        }

        private String generateDefaultId() {
            // Generate default id logic
            return "your-default-id";
        }
    }

    // Register the custom converter
    public static MongoCustomConversions mongoCustomConversions() {
        List<Converter<?, ?>> converters = Collections.singletonList(new IdConverter());
        return new MongoCustomConversions(converters);
    }*/
    @Indexed(name = "indxnm")
    private String name;
    private String docx;
    private String checksum;
    private String description;


    public void setName(String name) {
        this.name = name;
    }
    //public void setId(String id) {        this.id = id;}

    public void setDocx(String docx) {
        this.docx = docx;
    }

    public void setChecksum(String docx) {

        this.checksum = DigestUtils.sha256Hex(docx);
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
