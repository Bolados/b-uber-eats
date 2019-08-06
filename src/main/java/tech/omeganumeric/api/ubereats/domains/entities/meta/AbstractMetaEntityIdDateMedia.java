package tech.omeganumeric.api.ubereats.domains.entities.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractMetaEntityIdDateMedia extends AbstractMetaEntityIdDate {

    @Column(nullable = false, unique = true)
    private String src;

    @Column(nullable = false)
    private String mineType;


}
