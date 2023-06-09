package serv.co.documentservice.model;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private String id;
    private String name;
    private String type;
    private byte[] image;


}