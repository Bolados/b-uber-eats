package tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.omeganumeric.api.ubereats.webservice.security.domains.entities.UserApi;
import tech.omeganumeric.api.ubereats.webservice.security.repositories.UserApiRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JsonWebTokenUserDetailsService implements UserDetailsService {

    private final UserApiRepository repository;

    public JsonWebTokenUserDetailsService(UserApiRepository repository) {
        this.repository = repository;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserApi user = this.repository
                .findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
        return new JsonWebTokenUser(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
                        .stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
                        .collect(Collectors.toList()),
                user.isEnable(),
                Date.from(ZonedDateTime.of(user.getLastPasswordResetDate(), ZoneId.systemDefault()).toInstant()),
                true
        );
    }


}
