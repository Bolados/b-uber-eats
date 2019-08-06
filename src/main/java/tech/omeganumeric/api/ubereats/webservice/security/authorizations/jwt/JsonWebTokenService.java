package tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JsonWebTokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JsonWebTokenProvider provider;

    private final JsonWebTokenUserDetailsService service;


    public JsonWebTokenService(JsonWebTokenProvider provider, JsonWebTokenUserDetailsService service) {
        this.provider = provider;
        this.service = service;
    }

    /**
     * @param username or email
     * @return
     */
    public String generateToken(String username) {
        final UserDetails userDetails = this.service
                .loadUserByUsername(username);
        return this.provider.generateToken(userDetails);
    }

    public String generateToken(UserDetails userDetails) {
        return this.provider.generateToken(userDetails);
    }


    /**
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        JsonWebTokenUser jsonWebTokenUser = (JsonWebTokenUser) service
                .loadUserByUsername(
                        provider.getUsernameFromToken(token)
                );
        if (!provider.canTokenBeRefreshed(token, jsonWebTokenUser.getLastPasswordResetDate())) {
            throw new JsonWebTokenException("Can not refresh token");
        }
        return provider.refreshToken(token);
    }

    /**
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        final UserDetails userDetails = service
                .loadUserByUsername(
                        provider.getUsernameFromToken(token)
                );
        return provider.validateToken(token, userDetails);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return provider.validateToken(token, userDetails);
    }

    /**
     * @param token
     * @return
     */
    public Authentication authenticate(String token) {
        final UserDetails userDetails = service
                .loadUserByUsername(
                        provider.getUsernameFromToken(token)
                );
        return new UsernamePasswordAuthenticationToken(
                userDetails, "",
                userDetails.getAuthorities()
        );
    }

    public Authentication authenticate(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails, "",
                userDetails.getAuthorities()
        );
    }

    /**
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request) {
        return provider.resolveToken(request);
    }

}
