package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.util.StringUtils;
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
@EqualsAndHashCode(callSuper = true, exclude = {"region", "departments"})
@ToString(callSuper = true, exclude = {"departments"})
@Table(
        name = Country.FIELD_ENTITY_TABLE_NAME,
        indexes = {@Index(columnList = "shape", name = "sidx_country_shape")},
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uk_country_name"),
                @UniqueConstraint(columnNames = "code2", name = "uk_country_code2"),
                @UniqueConstraint(columnNames = "code3", name = "uk_country_code3"),
                @UniqueConstraint(columnNames = "domain", name = "uk_country_domain"),
                @UniqueConstraint(columnNames = "phoneCode", name = "uk_country_phoneCode")
        })
@Log
public class Country extends AbstractMetaEntityIdDateGeometry {


    public static final String FIELD_ENTITY = "country";
    public static final String FIELD_ENTITY_TABLE_NAME = "countries";

    private static final long serialVersionUID = 7900624653695724617L;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String name;

    @Column
    private String variant;

    @Column(length = 2)
    @NotNull
    @Size(min = 1, max = 2)
    private String code2;

    @Column(length = 3)
    @Size(min = 2, max = 3)
    private String code3;

    @Column(length = 3)
    @Size(min = 1, max = 3)
    private String phoneCode;

    @Column
    private Long population;

    @Column
    private Double density;

    @Column
    @Size(min = 2, max = 5)
    private String domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region", nullable = false)
    @JsonIgnoreProperties(value = {"countries"})
    @NotNull
    private Region region;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"country"})
    @Builder.Default
    private Set<Department> departments = new HashSet<>();

    public void updateAssociations() {
        if (this.getDepartments() != null) {
            for (Department department : this.getDepartments()) {
                department.setCountry(this);
            }
        }
    }

    private void basics() {
        if (this.getDomain() != null) {
            this.setDomain(this.getDomain().toUpperCase());
        }
        this.setCode2(this.getCode2().toUpperCase());
        if (this.getCode3() != null) {
            this.setCode3(this.getCode3().toUpperCase());
        } else {
            this.setCode3(this.getCode2());
        }
        this.setName(StringUtils.capitalize(this.getName()));
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
