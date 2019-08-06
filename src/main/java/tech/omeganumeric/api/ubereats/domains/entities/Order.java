package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.omeganumeric.api.ubereats.domains.entities.embeddable.OrderId;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"payment", "status", "user", "restaurant", "menu"})
@Table(name = Order.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code", name = "uk_order_code"),
                @UniqueConstraint(
                        columnNames = {"code", "restaurant_id", "menu_id", "user_id"},
                        name = "uk_order_ids")
        }

)
public class Order extends AbstractMetaEntityDate {

    public static final String FIELD_ENTITY = "order";
    public static final String FIELD_ENTITY_TABLE_NAME = "orders";
    private static final long serialVersionUID = 7691304461650702232L;

    @EmbeddedId
    private OrderId id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status", nullable = false)
    @JsonIgnoreProperties(value = {"orders"})
    @NotNull
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment")
    @JsonIgnoreProperties(value = {"orders"})
    private Payment payment;

    @Column
    private String information;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("restaurantId")
    @JsonIgnoreProperties(value = {"orders"})
    @NotNull
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menuId")
    @JsonIgnoreProperties(value = {"orders"})
    @NotNull
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JsonIgnoreProperties(value = {"orders"})
    @NotNull
    private User user;

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
