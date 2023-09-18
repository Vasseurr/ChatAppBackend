package com.vasseurr.chatApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vasseurr.chatApp.dto.base.BaseDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class UserDto extends BaseDto {

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String password;

    private String photoLink;

}