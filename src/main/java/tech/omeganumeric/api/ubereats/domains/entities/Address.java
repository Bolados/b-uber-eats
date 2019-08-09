package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDateLocation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"town", "residents", "locations", "deliveries", "addressSavedBy"})
@ToString(callSuper = true, exclude = {"residents", "locations", "deliveries", "addressSavedBy"})
@Table(
        name = Address.FIELD_ENTITY_TABLE_NAME
)
public class Address extends AbstractMetaEntityIdDateLocation {

    public static final String FIELD_ENTITY = "address";
    public static final String FIELD_ENTITY_TABLE_NAME = "addresses";
    private static final long serialVersionUID = 8913343380981392468L;
    @Column(nullable = false)
    @NotNull
    private String street;

    @Column(nullable = false)
    @NotNull
    private String building;

    @Column(nullable = false)
    @NotNull
    private String room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town", nullable = false)
    @JsonIgnoreProperties(value = {"addresses"})
    @NotNull
    private Town town;

    @OneToMany(mappedBy = "residence", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"residence"})
    @Builder.Default
    private Set<User> residents = new HashSet<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"locations"})
    @Builder.Default
    private Set<User> locations = new HashSet<>();

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"deliveries"})
    @Builder.Default
    private Set<User> deliveries = new HashSet<>();

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"restaurants"})
    @Builder.Default
    private Set<Restaurant> restaurants = new HashSet<>();

    @ManyToMany(mappedBy = "savedAddresses")
    @JsonIgnoreProperties(value = {"savedAddresses"})
    @Builder.Default
    private Set<User> addressSavedBy = new HashSet<>();

    public void updateAssociations() {
    }

    private void basics() {
        this.setRoom(this.getRoom().toUpperCase());
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
