package tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Created by stephan on 20.03.16.
 */
public class JsonWebTokenUser implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 8732274904815463622L;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final Date lastPasswordResetDate;
    private boolean connected;


    /**
     * @param username
     * @param password
     * @param authorities
     * @param enabled
     * @param lastPasswordResetDate
     */
    public JsonWebTokenUser(String username, String password,
                            Collection<? extends GrantedAuthority> authorities,
                            boolean enabled, Date lastPasswordResetDate,
                            boolean isAuth) {
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.username = username;
        this.connected = isAuth;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return this.lastPasswordResetDate;
    }

}
