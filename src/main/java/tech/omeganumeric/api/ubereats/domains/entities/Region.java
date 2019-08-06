package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = Region.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code", "name"}, name = "uk_region_code_name_orientation")
        })
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"countries"})
@ToString(callSuper = true, exclude = "countries")
public class Region extends AbstractMetaEntityIdDate {

    public static final String FIELD_ENTITY = "region";
    public static final String FIELD_ENTITY_TABLE_NAME = "regions";
    public static final String FIELD_CODE = "code";
    public static final String PKEY_FIELD_NAME = Region.FIELD_CODE;
    public static final String FIELD_NAME = "name";
    public static final String PKEY_FIELDS_NAME = Region.PKEY_FIELD_NAME + " | " + Region.FIELD_NAME;


    private static final long serialVersionUID = 7307014067019151649L;
    @NaturalId(mutable = true)
    @Column(length = 2, nullable = false)
    @NotNull
    @NotEmpty
    @NotBlank
    private String code;

    @Column(nullable = false)
    @NotNull
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"region"})
    @Builder.Default
    private Set<Country> countries = new HashSet<>();

    public void updateAssociations() {
    }

    private void basics() {
        this.code = this.getCode().toUpperCase();
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

