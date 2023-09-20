package com.vasseurr.chatApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vasseurr.chatApp.dto.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto extends BaseDto {

    private UserDto sender;

    //todo: convert to List<UserDto> for group chat rooms
    private UserDto receiver;

    private String content;

    private RoomDto room;
}