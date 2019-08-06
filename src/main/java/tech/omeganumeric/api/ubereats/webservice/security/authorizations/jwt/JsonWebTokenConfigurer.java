package tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonWebTokenConfigurer
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JsonWebTokenService jwtService;
    private final JsonWebTokenUserDetailsService jwtUserDetailsService;
    private final JsonWebTokenProvider jsonWebTokenProvider;

    public JsonWebTokenConfigurer(JsonWebTokenService jwtService, JsonWebTokenUserDetailsService jwtUserDetailsService, JsonWebTokenProvider jsonWebTokenProvider) {
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jsonWebTokenProvider = jsonWebTokenProvider;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        JsonWebTokenFilter filter = new JsonWebTokenFilter(
                jwtService,
                jwtUserDetailsService,
                jsonWebTokenProvider
        );
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
