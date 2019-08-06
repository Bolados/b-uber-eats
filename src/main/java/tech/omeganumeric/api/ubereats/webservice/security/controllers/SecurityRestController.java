package tech.omeganumeric.api.ubereats.webservice.security.controllers;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import tech.omeganumeric.api.ubereats.configs.AppConfig;
import tech.omeganumeric.api.ubereats.exceptions.ControllerException;
import tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt.JsonWebTokenProvider;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.AuthenticationRequest;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.AuthenticationResponse;
import tech.omeganumeric.api.ubereats.webservice.security.rest.resources.AuthenticationResponseResource;
import tech.omeganumeric.api.ubereats.webservice.security.rest.resources.UserApiResource;
import tech.omeganumeric.api.ubereats.webservice.security.services.AuthService;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Optional;


/**
 * @author BSCAKO
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = SecurityRestController.PATH)
@Api(
        value = SecurityRestController.PATH,
        consumes = AppConfig.API_CONSUMES,
        produces = AppConfig.API_PRODUCES,
        tags = {"API SECURITY"},
        description = " All services relating to access webservices"
)
public class SecurityRestController extends ControllerException {


    public static final String PATH = "api/secure";
    public static final String AUTH = "auth";
    public static final String USER = "user";
    public static final String LOGOUT = "logout";

    private final AuthService authService;
    private final JsonWebTokenProvider jwtProvider;

    public SecurityRestController(AuthService authService, JsonWebTokenProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
    }


    @RequestMapping(
            value = AUTH,
            method = RequestMethod.POST
    )
    public ResponseEntity<AuthenticationResponseResource> authUserAPi(@RequestBody AuthenticationRequest data, HttpServletResponse response) {
        log.debug("Credentials: {}", data);
        AuthenticationResponse authResponse = authService.authenticate(data);
        response.addHeader(
                jwtProvider.header,
                jwtProvider.bearer
                        + " " + authResponse.getToken()
        );
        final URI uri =
                MvcUriComponentsBuilder.fromController(SecurityUserRestController.class)
                        .path("/{username}")
                        .buildAndExpand(authResponse.getUsername())
                        .toUri();
        return ResponseEntity.created(uri).body(new AuthenticationResponseResource(authResponse));
    }

    @RequestMapping(
            value = USER,
            method = RequestMethod.GET
    )
    public ResponseEntity<UserApiResource> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return Optional.ofNullable(authService.authenticatedUser(userDetails))
                .map(p -> ResponseEntity.ok(new UserApiResource(p)))
                .orElseThrow(() -> new UsernameNotFoundException("Not found username :" + userDetails.getUsername()));
    }


    @RequestMapping(
            value = LOGOUT,
            method = RequestMethod.GET
    )
    public ResponseEntity logout(@AuthenticationPrincipal final UserDetails userDetails, HttpServletResponse response) {
        authService.logout(userDetails);
        response.addHeader(
                jwtProvider.header, null
        );
        return ResponseEntity.ok(null);
    }
}
