package tech.omeganumeric.api.ubereats.domains.entities.embeddable;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class OrderId extends RestaurantMenuId {

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @Column(name = "code")
    private String code;

}
