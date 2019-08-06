package tech.omeganumeric.api.ubereats.domains.entities.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true)
public abstract class AbstractMetaEntityIdDate extends AbstractMetaEntityId {

    @JsonIgnore
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @JsonIgnore
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
