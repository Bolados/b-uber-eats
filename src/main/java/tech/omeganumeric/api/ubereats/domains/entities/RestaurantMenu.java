package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.omeganumeric.api.ubereats.domains.entities.embeddable.RestaurantMenuId;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"restaurant", "menu"})
@Table(name = RestaurantMenu.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"restaurant_id", "menu_id"},
                        name = "uk_restaurant_menu_ids")
        }

)
public class RestaurantMenu extends AbstractMetaEntityDate {

    public static final String FIELD_ENTITY = "Restaurant_Menu";
    public static final String FIELD_ENTITY_TABLE_NAME = "Restaurants_Menus";
    private static final long serialVersionUID = 7691304461650702232L;

    @EmbeddedId
    private RestaurantMenuId id;

    @Column(nullable = false)
    @NotNull
    private Double reduction;

    @Column
    private String information;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("restaurantId")
    @JsonIgnoreProperties(value = {"menus"})
    @NotNull
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menuId")
    @JsonIgnoreProperties(value = {"restaurants"})
    @NotNull
    private Menu menu;

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
