package tech.omeganumeric.api.ubereats.webservice.security.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString
public class AuthenticationResponse {

    private String username;

    private String token;

}
