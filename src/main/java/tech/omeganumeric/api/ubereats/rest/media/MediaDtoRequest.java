package tech.omeganumeric.api.ubereats.rest.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.omeganumeric.api.ubereats.domains.entities.Menu;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MediaDtoRequest {

    @NotNull
    private String name;

    private Menu menu;

}
