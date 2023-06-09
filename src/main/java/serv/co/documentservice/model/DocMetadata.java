package serv.co.documentservice.model;

import lombok.*;

import java.time.LocalDateTime;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocMetadata {
    private String docuName;
    private String natureDocu;
    private long size;
    // Add any additional metadata fields you require
    private LocalDateTime creationTime;
    private String creePar;


    // Constructors, getters, and setters

}
