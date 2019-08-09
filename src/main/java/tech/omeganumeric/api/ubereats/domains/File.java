package tech.omeganumeric.api.ubereats.domains;

import lombok.*;
import org.springframework.core.io.Resource;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class File {

    private String name;

    private String type;

    private Resource data;
}
