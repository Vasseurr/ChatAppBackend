package com.vasseurr.chatApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vasseurr.chatApp.dto.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDto extends BaseDto {

    private Set<UserDto> users = new HashSet<>();

    private UserDto onlineUser;

    private boolean createdBefore;

}
