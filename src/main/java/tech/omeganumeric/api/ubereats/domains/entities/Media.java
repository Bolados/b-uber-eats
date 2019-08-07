package tech.omeganumeric.api.ubereats.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.omeganumeric.api.ubereats.domains.entities.meta.AbstractMetaEntityIdDateMedia;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(
        name = Media.FIELD_ENTITY_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uk_media_name")
        })
public class Media extends AbstractMetaEntityIdDateMedia {

    public static final String FIELD_ENTITY = "media";
    public static final String FIELD_ENTITY_TABLE_NAME = "medias";

    private static final long serialVersionUID = -6941448217079263647L;

    @OneToOne(mappedBy = "media")
    @JsonIgnoreProperties(value = {"media"})
    @Builder.Default
    Menu menu = null;

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
