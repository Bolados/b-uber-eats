package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"address", "phones", "menus", "orders"})
@ToString(callSuper = true, exclude = {"phones", "menus", "orders"})
@Table(
        name = Restaurant.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uk_restaurant_name"),
                @UniqueConstraint(columnNames = "email", name = "uk_restaurant_email"),
                @UniqueConstraint(columnNames = "site", name = "uk_restaurant_site"),
                @UniqueConstraint(columnNames = {"name", "email", "site"}, name = "uk_restaurant__name_email_site")
        })
public class Restaurant extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "Restaurant";
    public static final String FIELD_ENTITY_TABLE_NAME = "Restaurants";
    private static final long serialVersionUID = 6081174092958743234L;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    private String email;

    @Column
    private String site;

    @Column
    private String information;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address", nullable = false)
    @JsonIgnoreProperties(value = {"restaurants"})
    @NotNull
    private Address address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"restaurant"})
    @Builder.Default
    private Set<Phone> phones = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"restaurant"})
    @Builder.Default
    private Set<RestaurantMenu> menus = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"restaurant"})
    @Builder.Default
    private Set<Order> orders = new HashSet<>();

    public void updateAssociations() {
    }

    private void basics() {

    }

    @PrePersist
    public void beforePersist() {
        this.basics();
        this.updateAssociations();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.basics();
        this.updateAssociations();
    }

    @PreRemove
    public void beforeRemove() {
        this.basics();
    }


}
