package com.vasseurr.chatApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {

    private Long senderId;

    private Long receiverId;

    private String content;
}