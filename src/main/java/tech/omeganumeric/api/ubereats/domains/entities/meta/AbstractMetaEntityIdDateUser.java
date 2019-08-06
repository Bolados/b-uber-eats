package tech.omeganumeric.api.ubereats.domains.entities.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractMetaEntityIdDateUser extends AbstractMetaEntityIdDate {

    @CreatedBy
    @Column(updatable = false)
    @JsonIgnore
    private Long createdBy;

    @LastModifiedBy
    @Column()
    @JsonIgnore
    private Long updatedBy;


}
