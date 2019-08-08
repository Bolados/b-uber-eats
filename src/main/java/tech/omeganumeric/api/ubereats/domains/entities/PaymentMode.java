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
@EqualsAndHashCode(callSuper = true, exclude = {"payments"})
@ToString(callSuper = true, exclude = {"payments"})
@Table(
        name = PaymentMode.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "mode", name = "uk_payment_mode")
        })
public class PaymentMode extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "Payment_Mode";
    public static final String FIELD_ENTITY_TABLE_NAME = "Payment_Modes";


    private static final long serialVersionUID = 3980086006604530309L;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @NotEmpty
    private String mode;

    @Column
    private String description;

    @OneToMany(mappedBy = "mode", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"mode"})
    @Builder.Default
    private Set<Payment> payments = new HashSet<>();


    public void updateAssociations() {
    }

    private void basics() {
        this.setMode(this.getMode().toUpperCase());

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
