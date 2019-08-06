package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDate;

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
@EqualsAndHashCode(callSuper = true, exclude = {"orders", "mode"})
@ToString(callSuper = true, exclude = {"orders"})
@Table(
        name = Payment.FIELD_ENTITY_TABLE_NAME
)
public class Payment extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "payment";
    public static final String FIELD_ENTITY_TABLE_NAME = "Payments";

    private static final long serialVersionUID = -8603788421741922465L;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payment", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"payment"})
    @Builder.Default
    private Set<Order> orders = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mode", nullable = false)
    @JsonIgnoreProperties(value = {"payments"})
    @NotNull
    private PaymentMode mode;

    @Column(nullable = false)
    @NotNull
    private Double amount;

    // TODO : Think about best implementation
    private String allInformationAboutTransaction;

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
