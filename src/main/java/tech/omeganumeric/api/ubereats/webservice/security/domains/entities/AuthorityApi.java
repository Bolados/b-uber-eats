package tech.omeganumeric.api.ubereats.webservice.security.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = AuthorityApi.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}, name = "uk_api_authority_name")
        })
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"users"})
@ToString(callSuper = true, exclude = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityApi extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "api_authority";
    public static final String FIELD_ENTITY_TABLE_NAME = "api_authorities";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";

    private static final long serialVersionUID = 5049453559169591098L;

    @NaturalId(mutable = true)
    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Column
    private String description;

    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE,
//            CascadeType.REFRESH,
//            CascadeType.DETACH
            CascadeType.ALL
    })
    @JoinTable(
            name = "api_users_authorities",
            joinColumns = @JoinColumn(
                    name = "authority_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    @JsonIgnoreProperties(value = {"authorities"})
    @Builder.Default
    private Set<UserApi> users = new HashSet<>();

    private void basics() {
        this.name = this.getName().toUpperCase();
    }

    @PrePersist
    public void beforePersist() {
        this.basics();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.basics();
    }

    @PreRemove
    public void beforeRemove() {
        this.basics();
    }

}
