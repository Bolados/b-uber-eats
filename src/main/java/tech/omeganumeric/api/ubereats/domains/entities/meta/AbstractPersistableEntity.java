package tech.omeganumeric.api.ubereats.domains.entities.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public abstract class AbstractPersistableEntity<ID> implements Serializable, Cloneable {

    @Id
    @GeneratedValue(generator = "SequencePerEntityGenerator")
    private ID id;

    @Version
    private ID version;

}
