package tech.omeganumeric.api.ubereats.webservice.security.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.internal.NotNull;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tech.omeganumeric.api.ubereats.domains.converters.LocalDateTimeDeserializer;
import tech.omeganumeric.api.ubereats.domains.converters.LocalDateTimeSerializer;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(
        callSuper = true,
        exclude = {"authorities"}
)
@ToString(callSuper = true, exclude = {"authorities"})
@Table(
        name = UserApi.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username", name = "uk_user_api_username"),
                @UniqueConstraint(columnNames = "email", name = "uk_user_api_email"),
                @UniqueConstraint(columnNames = {"username", "email"}, name = "uk_user_api_username_email")
        })

@EntityListeners(AuditingEntityListener.class)
public class UserApi extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "api_user";
    public static final String FIELD_ENTITY_TABLE_NAME = "api_users";

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    private String username;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    private String firstName;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    private String lastName;

    @JsonIgnore
    @Column(nullable = false)
    @Builder.Default
    private boolean enable = true;

    @JsonIgnore
    @Column
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @LastModifiedDate
    private LocalDateTime lastPasswordResetDate;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"users"})
    @Builder.Default
    private Set<AuthorityApi> authorities = new HashSet<>();

    //    @Transient
//    private UserApiRepository repository() {
//        return (UserApiRepository) AutowiringInEntityConfig.contextProvider()
//                .getApplicationContext()
//                .getBean("userApiRepository");
//    }
//
//    @PrePersist
//    public void beforePersist() {
//        lastPasswordResetDate = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
//    }
//
//    @PreUpdate
//    public  void beforeUpdate() {
//        Optional<UserApi> old = repository().findByUsernameOrEmail(username);
//        if(old.isPresent() && getLastPasswordResetDate() != null && getLastPasswordResetDate() != old.get().getLastPasswordResetDate()) {
//            lastPasswordResetDate = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
//        }
//    }
}
