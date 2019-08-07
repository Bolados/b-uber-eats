package tech.omeganumeric.api.ubereats.webservice.security.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.omeganumeric.api.ubereats.controllers.OTPResourceController;
import tech.omeganumeric.api.ubereats.repositories.*;
import tech.omeganumeric.api.ubereats.webservice.security.authorizations.AuthorizationEntryPoint;
import tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt.JsonWebTokenConfigurer;
import tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt.JsonWebTokenProvider;
import tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt.JsonWebTokenService;
import tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt.JsonWebTokenUserDetailsService;
import tech.omeganumeric.api.ubereats.webservice.security.controllers.SecurityRestController;
import tech.omeganumeric.api.ubereats.webservice.security.controllers.SecurityUserRestController;
import tech.omeganumeric.api.ubereats.webservice.security.repositories.AuthorityApiRepository;
import tech.omeganumeric.api.ubereats.webservice.security.repositories.UserApiRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SEPARATOR = "/";
    private static final String ALL = SEPARATOR + "**";
    private static final String PRE_REPOSITORY_PATH = "";
    private static final String PROFILE_PATH = "profile";

    private static final String[] AUTH_WHITELIST = {
            PROFILE_PATH, PROFILE_PATH + ALL,
            SecurityRestController.PATH + SEPARATOR + SecurityRestController.AUTH,
            PRE_REPOSITORY_PATH + RegionRepository.PATH,
            PRE_REPOSITORY_PATH + RegionRepository.PATH + ALL,

            PRE_REPOSITORY_PATH + RoleRepository.PATH,
            PRE_REPOSITORY_PATH + RoleRepository.PATH + ALL,
//            PRE_REPOSITORY_PATH + MediaRepository.PATH,
//            PRE_REPOSITORY_PATH + MediaRepository.PATH + ALL,
            PRE_REPOSITORY_PATH + OrderStatusRepository.PATH,
            PRE_REPOSITORY_PATH + OrderStatusRepository.PATH + ALL,
            PRE_REPOSITORY_PATH + PhoneRepository.PATH,
            PRE_REPOSITORY_PATH + PhoneRepository.PATH + ALL,
            PRE_REPOSITORY_PATH + PaymentModeRepository.PATH,
            PRE_REPOSITORY_PATH + PaymentModeRepository.PATH + ALL,
    };

    private static final String[] AUTH_BLACKLIST = {
            SecurityRestController.PATH + SEPARATOR + SecurityRestController.USER,
            SecurityRestController.PATH + SEPARATOR + SecurityRestController.USER + ALL,
            SecurityRestController.PATH + SEPARATOR + SecurityRestController.LOGOUT,

            OTPResourceController.PATH,
            OTPResourceController.PATH + ALL
    };

    private static final String[] AUTH_BLACKLIST_ADMIN = {
            SecurityUserRestController.PATH,
            SecurityUserRestController.PATH + ALL,
            PRE_REPOSITORY_PATH + UserApiRepository.PATH,
            PRE_REPOSITORY_PATH + UserApiRepository.PATH + ALL,
            PRE_REPOSITORY_PATH + AuthorityApiRepository.PATH,
            PRE_REPOSITORY_PATH + AuthorityApiRepository.PATH + ALL,

    };


    private final AuthorizationEntryPoint unauthorizedHandler;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JsonWebTokenService jwtService;
    private final JsonWebTokenUserDetailsService jwtUserDetailsService;
    private final JsonWebTokenProvider jsonWebTokenProvider;

    public SecurityConfig(AuthorizationEntryPoint unauthorizedHandler, AuthenticationManagerBuilder authenticationManagerBuilder, JsonWebTokenService jwtService, JsonWebTokenUserDetailsService jwtUserDetailsService, JsonWebTokenProvider jsonWebTokenProvider) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jsonWebTokenProvider = jsonWebTokenProvider;
    }


    public void configureGlobal() throws Exception {
        authenticationManagerBuilder
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)

                .and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                .antMatchers(AUTH_WHITELIST).permitAll()

                .antMatchers(AUTH_BLACKLIST)

                .authenticated()

                .antMatchers(AUTH_BLACKLIST_ADMIN)

                .authenticated()

//         .and().requiresChannel().
                .and()
                .apply(new JsonWebTokenConfigurer(
                        jwtService,
                        jwtUserDetailsService,
                        jsonWebTokenProvider
                ))

                // disable page caching
                .and()
                .headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl()

        ;


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
                // .ignoring()
                // .antMatchers(
                // HttpMethod.POST,
                // this.authenticationPath
                // )
                //
                // // allow anonymous resource requests
                // .and()
                .ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                );

        // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
        // .and().ignoring().antMatchers("/h2-console/**/**");
    }


}
