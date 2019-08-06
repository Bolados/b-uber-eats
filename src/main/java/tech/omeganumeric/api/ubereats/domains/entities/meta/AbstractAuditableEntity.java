package tech.omeganumeric.api.ubereats.domains.entities.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableEntity<Entity, ID>
        extends AbstractPersistableEntity<ID> implements Serializable, Cloneable {
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by")
    Entity createdBy;
    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    Entity lastModifiedBy;
    @JsonIgnore
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @JsonIgnore
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
