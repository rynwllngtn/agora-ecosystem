package dev.rynwllngtn.common.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@MappedSuperclass
public abstract class AuditableEntity extends TimestampedEntity {
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;
}