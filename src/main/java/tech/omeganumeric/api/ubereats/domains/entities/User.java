package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@RestResource
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(
        callSuper = true,
        exclude = {"residence", "location", "delivery", "savedAddresses", "orders", "roles"}
)
@ToString(callSuper = true, exclude = {"savedAddresses", "orders", "roles"})
@Table(
        name = User.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phone", name = "uk_user_phone"),
                @UniqueConstraint(columnNames = "email", name = "uk_user_email"),
                @UniqueConstraint(columnNames = "login", name = "uk_user_login"),
                @UniqueConstraint(columnNames = {"phone", "email", "login"}, name = "uk_user_phone_email_login")
        })
public class User extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "user";
    public static final String FIELD_ENTITY_TABLE_NAME = "users";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_EMAIL = "email";
    public static final String PKEY_FIELD_NAME = User.FIELD_EMAIL;
    public static final String FIELD_LOGIN = "login";
    public static final String PKEY_FIELDS_NAME = User.PKEY_FIELD_NAME
            + " | " + User.FIELD_PHONE + " | " + User.FIELD_LOGIN;
    public static final String FIELD_NAME = "name";

    private static final long serialVersionUID = 3679301844774105320L;

    @OneToOne
    @MapsId("number")
    @JoinColumn(name = "phone", unique = true, nullable = false)
    @JsonIgnoreProperties(value = {"user"})
    @NotNull
    @NotEmpty
    @NotBlank
    private Phone phone;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    private String login;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8)
    private String password;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residence", nullable = false)
    @JsonIgnoreProperties(value = {"residents"})
    @NotNull
    private Address residence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    @JsonIgnoreProperties(value = {"locations"})
    @Builder.Default
    private Address location = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery", nullable = false)
    @JsonIgnoreProperties(value = {"deliveries"})
    @NotNull
    private Address delivery;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"user"})
    @Builder.Default
    private Set<Order> orders = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_saved_addresses",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
    @JsonIgnoreProperties(value = {"addressSavedBy"})
    @Builder.Default
    private Set<Address> savedAddresses = new HashSet<>();

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"users"})
    @Builder.Default
    private Set<Role> roles = new HashSet<>();


    private void basics() {
    }

    private void checkAddressesByRoles() {

    }

    private void updateAssociation() {
    }

    @PrePersist
    public void beforePersist() {
        this.checkAddressesByRoles();
        this.basics();
        this.updateAssociation();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.checkAddressesByRoles();
        this.basics();
        this.updateAssociation();
    }

    @PreRemove
    public void beforeRemove() {
        this.basics();
        this.updateAssociation();
    }

}
