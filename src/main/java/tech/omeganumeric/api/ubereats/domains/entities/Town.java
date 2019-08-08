package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDateGeometry;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@RestResource
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"district"})
@ToString(callSuper = true)
@Table(
        name = Town.FIELD_ENTITY_TABLE_NAME,
        indexes = {@Index(columnList = "shape", name = "sidx_town_shape")},
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uk_town_name")
        })
public class Town extends AbstractMetaEntityIdDateGeometry {

    public static final String FIELD_ENTITY = "Town";
    public static final String FIELD_ENTITY_TABLE_NAME = "Towns";

    private static final long serialVersionUID = 177223631740299185L;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column
    private String variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties(value = {"towns"})
    @NotNull
    private District district;

    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"town"})
    @Builder.Default
    private Set<Address> addresses = new HashSet<>();

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
