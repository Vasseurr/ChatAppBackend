package com.vasseurr.chatApp.service;

import com.vasseurr.chatApp.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findById(String id);

    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);

    UserDto delete(UserDto userDto);

    List<UserDto> listAll();
}
