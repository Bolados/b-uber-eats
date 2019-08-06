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
@EqualsAndHashCode(callSuper = true, exclude = {"status"})
@ToString(callSuper = true, exclude = {"status"})
@Table(
        name = OrderStatus.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "status", name = "uk_order_status")
        })
public class OrderStatus extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "order_status";
    public static final String FIELD_ENTITY_TABLE_NAME = "orders_status";


    private static final long serialVersionUID = 3980086006604530309L;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    private String status;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"status"})
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
