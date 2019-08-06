package tech.omeganumeric.api.ubereats.webservice.security.rest.resources;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import tech.omeganumeric.api.ubereats.webservice.security.controllers.SecurityUserRestController;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.UserApiDto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class UserApiResource extends ResourceSupport {

    private final UserApiDto user;

    public UserApiResource(final UserApiDto user) {
        this.user = user;
        add(linkTo(methodOn(SecurityUserRestController.class).get(user.getUsername())).withSelfRel());
    }
}
