package com.vasseurr.chatApp.service;

import com.vasseurr.chatApp.dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto findById(String id);

    RoomDto save(RoomDto roomDto);

    RoomDto update(RoomDto roomDto);

    RoomDto delete(RoomDto roomDto);

    List<RoomDto> listAll();

}
