package tech.omeganumeric.api.ubereats.rest.media;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;
import tech.omeganumeric.api.ubereats.domains.entities.Menu;

@Data
@Builder
public class MediaDtoResponse {

    private Long id;

    private String name;

    private String type;

    private Resource data;

    private Menu menu;


}
