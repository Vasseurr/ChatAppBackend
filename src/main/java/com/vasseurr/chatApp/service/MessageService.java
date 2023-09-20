package com.vasseurr.chatApp.service;

import com.vasseurr.chatApp.dto.MessageDto;

import java.util.List;

public interface MessageService {

    MessageDto findById(String id);

    MessageDto save(MessageDto messageDto);

    MessageDto update(MessageDto messageDto);

    MessageDto delete(MessageDto messageDto);

    List<MessageDto> listAll();

    List<MessageDto> listAllByRoom(String roomId);
}
