package tech.omeganumeric.api.ubereats.domains.entities.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractMetaEntityId extends AbstractMetaEntity {

    @Id
    @GeneratedValue(generator = "SequencePerEntityGenerator")
    private Long id;


}
