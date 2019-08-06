package tech.omeganumeric.api.ubereats.domains.entities.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class RestaurantMenuId implements Serializable {

    @Column(name = "restaurant_id")
    @NotNull
    private Long restaurantId;

    @Column(name = "menu_id")
    @NotNull
    private Long menuId;

}
