package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDate;
import tech.omeganumeric.api.ubereats.validators.EntityMetaValidator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = Phone.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "number", name = "uk_phone_number")
        })
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"user", "restaurant"})
@ToString(callSuper = true)
public class Phone extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "Phone";
    public static final String FIELD_ENTITY_TABLE_NAME = "Phones";
    public static final String FIELD_NUMBER = "number";
    public static final String PKEY_FIELD_NAME = Phone.FIELD_NUMBER;
    public static final String PKEY_FIELDS_NAME = Phone.PKEY_FIELD_NAME;
    private static final long serialVersionUID = 3135100165282441128L;

    @NaturalId(mutable = true)
    @Column(nullable = false, length = 10)
    @Size(min = 1, max = 10)
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull(groups = {EntityMetaValidator.New.class, EntityMetaValidator.Update.class})
    @JsonView({EntityMetaValidator.Details.class})
    private String number;

    @OneToOne(mappedBy = "phone", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"phone"})
    @Builder.Default
    private User user = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant")
    @JsonIgnoreProperties(value = {"phones"})
    @Builder.Default
    private Restaurant restaurant = null;

    private void basics() {
        this.number = this.getNumber().toUpperCase();
    }

    @PrePersist
    public void beforePersist() {
        this.basics();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.basics();
    }

    @PreRemove
    public void beforeRemove() {
        this.basics();
    }

}
