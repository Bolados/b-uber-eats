package tech.omeganumeric.api.ubereats.webservice.security.rest.resources;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import tech.omeganumeric.api.ubereats.webservice.security.controllers.SecurityUserRestController;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.AuthenticationResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class AuthenticationResponseResource extends ResourceSupport {

    private final AuthenticationResponse response;

    public AuthenticationResponseResource(final AuthenticationResponse response) {
        this.response = response;
        add(linkTo(methodOn(SecurityUserRestController.class).get(response.getUsername())).withSelfRel());
    }
}
