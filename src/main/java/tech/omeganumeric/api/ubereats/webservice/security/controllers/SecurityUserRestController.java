package tech.omeganumeric.api.ubereats.webservice.security.controllers;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.omeganumeric.api.ubereats.configs.AppConfig;
import tech.omeganumeric.api.ubereats.exceptions.ControllerException;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.UserApiDto;
import tech.omeganumeric.api.ubereats.webservice.security.rest.resources.UserApiResource;
import tech.omeganumeric.api.ubereats.webservice.security.services.UserApiService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author BSCAKO
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = SecurityUserRestController.PATH)
@Api(
        value = SecurityUserRestController.PATH,
        consumes = AppConfig.API_CONSUMES,
        produces = AppConfig.API_PRODUCES,
        tags = {"API USERS"},
        description = " All services relating to manage api users"
)
public class SecurityUserRestController extends ControllerException {

    public static final String PATH = "api/users";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserApiService userApiService;

    public SecurityUserRestController(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resources<UserApiResource>> all() {
        final List<UserApiResource> collection =
                userApiService.findAll().stream()
                        .map(p -> new UserApiResource(userApiService.convertEntityToDto(p)))
                        .collect(Collectors.toList());
        final Resources<UserApiResource> resources = new Resources<>(collection);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @RequestMapping(
            value = "/{username}",
            method = RequestMethod.GET
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserApiResource> get(
            @PathVariable final String username
    ) {

        return Optional.ofNullable(userApiService.findByUsername(username))
                .map(p -> ResponseEntity.ok(new UserApiResource(userApiService.convertEntityToDto(p))))
                .orElseThrow(() -> new UsernameNotFoundException("Not found username :" + username));

    }


    @RequestMapping(
            method = RequestMethod.POST
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity registerUser(@Validated @RequestBody UserApiDto user) {
        final UserApiDto userApi = userApiService.save(user);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{username}")
                        .buildAndExpand(userApi.getUsername())
                        .toUri();
        return ResponseEntity.created(uri).body(userApi);
    }

    @RequestMapping(
            method = RequestMethod.PUT
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateUser(@RequestParam final String username, @Validated @RequestBody UserApiDto user) {
        final UserApiDto userApi = userApiService.update(username, user);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(userApi);
    }

    @RequestMapping(
            method = RequestMethod.DELETE
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteUser(@RequestParam final String username) {
        userApiService.delete(username);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(
            value = "/{username}",
            method = RequestMethod.PUT
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateUser1(@PathVariable final String username, @Validated @RequestBody UserApiDto user) {
        return updateUser(username, user);
    }

//	@RequestMapping(
//			method = RequestMethod.GET
//	)
//	public ResponseEntity<UserApiResource> get1(@RequestParam final String username) {
//		return this.get(username);
//	}


    @RequestMapping(
            value = "/{username}",
            method = RequestMethod.DELETE
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteUser1(@PathVariable final String username) {
        return deleteUser(username);
    }


}
