package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDateGeometry;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"department", "towns"})
@ToString(callSuper = true, exclude = "towns")
@Table(
        name = District.FIELD_ENTITY_TABLE_NAME,
        indexes = {@Index(columnList = "shape", name = "sidx_district_shape")},
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uk_district_name"),
                @UniqueConstraint(columnNames = "code", name = "uk_district_code")
        })
public class District extends AbstractMetaEntityIdDateGeometry {

    public static final String FIELD_ENTITY = "district";
    public static final String FIELD_ENTITY_TABLE_NAME = "districts";

    private static final long serialVersionUID = -3056870362784096051L;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String name;

    @Column
    private String variant;

    @Column(length = 3)
    @Size(max = 3)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties(value = {"departments"})
    @NotNull
    private Department department;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"district"})
    @Builder.Default
    private Set<Town> towns = new HashSet<>();


    public void updateAssociations() {
        for (Town town : this.towns) {
            town.setDistrict(this);
        }
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
