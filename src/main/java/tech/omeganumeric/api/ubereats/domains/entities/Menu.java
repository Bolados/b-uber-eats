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
@EqualsAndHashCode(callSuper = true, exclude = {"restaurants", "orders"})
@ToString(callSuper = true, exclude = {"restaurants", "orders"})
@Table(
        name = Menu.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code", name = "uk_menu_name"),
                @UniqueConstraint(columnNames = "media", name = "uk_menu_media"),
                @UniqueConstraint(columnNames = {"code", "media"}, name = "uk_menu_code_media")
        })
public class Menu extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "menu";
    public static final String FIELD_ENTITY_TABLE_NAME = "menus";
    private static final long serialVersionUID = -8527413576606613462L;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @NotEmpty
    private String code;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @NotEmpty
    private String price;

    @Column
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media", unique = true, nullable = false)
    @JsonIgnoreProperties(value = {"menu"})
    @NotNull
    private Media media;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"menu"})
    @Builder.Default
    private Set<RestaurantMenu> restaurants = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"menu"})
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
