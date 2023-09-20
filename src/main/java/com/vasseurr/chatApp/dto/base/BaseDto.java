package com.vasseurr.chatApp.dto.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vasseurr.chatApp.model.EntityStatus;
import com.vasseurr.chatApp.util.LocalDateTimeDeserializer;
import com.vasseurr.chatApp.util.LocalDateTimeSerializer;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    private String createdBy = "O_RUZGAR";

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedDate = LocalDateTime.now();

    private String modifiedBy = "O_RUZGAR";
}



