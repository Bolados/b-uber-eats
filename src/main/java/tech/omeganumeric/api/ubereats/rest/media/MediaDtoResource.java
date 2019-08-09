package tech.omeganumeric.api.ubereats.rest.media;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import tech.omeganumeric.api.ubereats.controllers.repository.MediaRepositoryController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class MediaDtoResource extends ResourceSupport {
    private final MediaDtoResponse media;

    public MediaDtoResource(final MediaDtoResponse media) {
        this.media = media;
        add(linkTo(methodOn(MediaRepositoryController.class).findMediaById(media.getId())).withSelfRel());
    }


}
