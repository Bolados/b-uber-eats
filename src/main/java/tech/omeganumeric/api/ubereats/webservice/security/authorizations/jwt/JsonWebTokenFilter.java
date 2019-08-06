package tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final JsonWebTokenService jwtService;
    private final JsonWebTokenUserDetailsService jwtUserDetailsService;
    private final JsonWebTokenProvider jsonWebTokenProvider;

    public JsonWebTokenFilter(
            JsonWebTokenService jwtService,
            JsonWebTokenUserDetailsService jwtUserDetailsService,
            JsonWebTokenProvider jsonWebTokenProvider
    ) {
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jsonWebTokenProvider = jsonWebTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = jsonWebTokenProvider.resolveToken(request);
        log.debug("Filter: Json Web Token : {} ", token);
        try {
            if (token != null) {
                final UserDetails userDetails = jwtUserDetailsService
                        .loadUserByUsername(
                                jsonWebTokenProvider.getUsernameFromToken(token)
                        );
                if (jsonWebTokenProvider.validateToken(token, userDetails)) {
                    Authentication auth = jwtService.authenticate(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.info(String.format(
                            "Authenticated user : %s  with token: %s  ",
                            userDetails.getUsername(),
                            token));
                }
            }
        } catch (ExpiredJwtException eje) {
            log.warn("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (UsernameNotFoundException e) {
            log.warn("Security exception for unknown user - Invalid token {} - {}", token, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (BadCredentialsException e) {
            log.warn("Security exception for unknown user - Bad Credentials {} - {} ", token, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        chain.doFilter(request, response);
    }
}
