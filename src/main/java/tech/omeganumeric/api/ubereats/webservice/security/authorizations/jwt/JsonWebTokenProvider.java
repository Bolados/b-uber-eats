package tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JsonWebTokenProvider implements Serializable {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "iat";
    private static final long serialVersionUID = 2212428894870252583L;
    @Value("${security.jwt.header:Authorization}")
    public String header = "Authorization";
    @Value("${security.jwt.bearer:Bearer}")
    public String bearer = "Bearer ";
    @SuppressFBWarnings(value = "SE_BAD_FIELD", justification = "It's okay here")
    private Clock clock = DefaultClock.INSTANCE;
    @Value("${security.jwt.secret-key:secret}")
    private String secret = "secret";
    @Value("${security.jwt.expire-length:3600000}")
    private long expiration = 3600000; // 1h

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(bearer)) {
            return bearerToken.substring(bearer.length());
        }
        return null;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }


    public String getUsernameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date exp = this.getExpirationDateFromToken(token);
        return exp.before(this.clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return ((lastPasswordReset != null) && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return this.doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = this.clock.now();
        final Date expirationDate = this.calculateExpirationDate(createdDate);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getIssuedAtDateFromToken(token);
        return !this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!this.isTokenExpired(token) || this.ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = this.clock.now();
        final Date expirationDate = this.calculateExpirationDate(createdDate);

        final Claims claims = this.getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        JsonWebTokenUser user = (JsonWebTokenUser) userDetails;
        final String username = this.getUsernameFromToken(token);
        final Date created = this.getIssuedAtDateFromToken(token);
        // final Date expiration = getExpirationDateFromToken(jwt);
        return (
                username.equals(user.getUsername())
                        && !this.isTokenExpired(token)
                        && !this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
        );
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + (this.expiration * 1000));
    }
}
