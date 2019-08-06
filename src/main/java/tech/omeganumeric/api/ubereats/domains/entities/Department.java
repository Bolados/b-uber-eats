package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDateGeometry;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
@EqualsAndHashCode(callSuper = true, exclude = {"country", "districts"})
@ToString(callSuper = true, exclude = "districts")
@Table(
        name = Department.FIELD_ENTITY_TABLE_NAME,
        indexes = {@Index(columnList = "shape", name = "sidx_department_shape")},
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uk_department_name"),
                @UniqueConstraint(columnNames = "code", name = "uk_department_code")
        })
public class Department extends AbstractMetaEntityIdDateGeometry {


    public static final String FIELD_ENTITY = "department";
    public static final String FIELD_ENTITY_TABLE_NAME = "department";

    private static final long serialVersionUID = -5035731125288512135L;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @NotEmpty
    private String name;

    @Column
    private String variant;

    @Column(length = 3)
    @Size(max = 3)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country", nullable = false)
    @JsonIgnoreProperties(value = {"departments"})
    @NotNull
    private Country country;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "department", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"department"})
    @Builder.Default
    private Set<District> districts = new HashSet<>();

    public void updateAssociations() {
        for (District district : this.districts) {
            district.setDepartment(this);
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
