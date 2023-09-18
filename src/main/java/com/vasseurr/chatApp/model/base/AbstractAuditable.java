package com.vasseurr.chatApp.model.base;


import com.vasseurr.chatApp.model.EntityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class AbstractAuditable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private EntityStatus entityStatus;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime modifiedDate;

    private String modifiedBy;

    @PrePersist
    private void prePersist() {
        if(createdDate == null) {
            setCreatedDate(LocalDateTime.now());
        }
        if(entityStatus == null) {
            setEntityStatus(EntityStatus.ACTIVE);
        }
        if(modifiedDate == null) {
            setModifiedDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void preUpdate() {
        setModifiedDate(LocalDateTime.now());
    }

}


