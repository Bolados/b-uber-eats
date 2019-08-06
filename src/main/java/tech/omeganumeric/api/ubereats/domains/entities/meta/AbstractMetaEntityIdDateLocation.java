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
public abstract class AbstractMetaEntityIdDateLocation extends AbstractMetaEntityIdDate {

    @Column
    private Long longitude;

    @Column
    private Long latitude;

    @Column
    private Double accuracy;


}
