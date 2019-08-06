package tech.omeganumeric.api.ubereats.webservice.security.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt.JsonWebTokenService;
import tech.omeganumeric.api.ubereats.webservice.security.domains.entities.UserApi;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.AuthenticationRequest;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.AuthenticationResponse;
import tech.omeganumeric.api.ubereats.webservice.security.rest.dto.UserApiDto;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JsonWebTokenService jwtService;
    private final UserApiService userApiService;

    public AuthService(AuthenticationManager authenticationManager, JsonWebTokenService jwtService, UserApiService userApiService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userApiService = userApiService;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest data) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getUsername(),
                        data.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AuthenticationResponse.builder()
                .username(data.getUsername())
                .token(jwtService.generateToken(data.getUsername()))
                .build()
                ;
    }

    public UserApiDto authenticatedUser(UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("Any user logged ! Please sign in ");
        }
        UserApi user = userApiService.findByUsername(userDetails.getUsername());
        return userApiService.convertEntityToDto(user);
    }

    public void logout(UserDetails userDetails) {
        SecurityContextHolder.getContext().setAuthentication(null);
    }


}
