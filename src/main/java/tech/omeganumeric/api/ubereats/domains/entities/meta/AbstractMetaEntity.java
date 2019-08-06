package tech.omeganumeric.api.ubereats.domains.entities.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;


@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public abstract class AbstractMetaEntity
        implements
        Serializable, Cloneable {

    @Version
//    @JsonView({EntityMetaValidator.AdminDetails.class})
    @JsonIgnore
    private Long version;
}
