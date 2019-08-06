package tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JsonWebTokenProperties {

    public final String header = "Authorization";
    public final String bearer = "Bearer ";
    private String secret = "secret";
    //validity in milliseconds
    private long validityInMs = 3600000; // 1h
}
