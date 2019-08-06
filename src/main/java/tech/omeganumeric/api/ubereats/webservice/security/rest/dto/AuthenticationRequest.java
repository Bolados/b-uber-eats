package tech.omeganumeric.api.ubereats.webservice.security.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString
public class AuthenticationRequest {

    @NotNull
    @NotEmpty
    @NotBlank
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;

}
