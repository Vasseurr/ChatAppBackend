package com.vasseurr.chatApp.dto.base;

import com.vasseurr.chatApp.model.EntityStatus;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseDto {

    private String id;

    private EntityStatus entityStatus = EntityStatus.ACTIVE;

    private LocalDateTime createdDate;

    private String createdBy = "O_RUZGAR";

    private LocalDateTime modifiedDate = LocalDateTime.now();

    private String modifiedBy = "O_RUZGAR";
}
