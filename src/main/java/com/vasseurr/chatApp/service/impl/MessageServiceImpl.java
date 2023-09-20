package com.vasseurr.chatApp.service.impl;

import com.vasseurr.chatApp.dto.MessageDto;
import com.vasseurr.chatApp.exception.NoSuchElementFoundException;
import com.vasseurr.chatApp.model.EntityStatus;
import com.vasseurr.chatApp.model.Message;
import com.vasseurr.chatApp.repository.MessageRepository;
import com.vasseurr.chatApp.service.MessageService;
import com.vasseurr.chatApp.util.MappingHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    private MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageDto findById(String id) {
        return MappingHelper.getMapper().map(messageRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementFoundException("No such message exists")), MessageDto.class);
    }

    @Override
    public MessageDto save(MessageDto messageDto) {
        Message message = MappingHelper.getMapper().map(messageDto, Message.class);
        message.setCreatedBy(message.getSender().getUserName());
        message.setModifiedBy(message.getSender().getUserName());
        //todo: room modified date is not updated
        message.getRoom().setModifiedDate(LocalDateTime.now());
        message = messageRepository.save(message);
        logger.info("{} saved by {}", message, message.getSender());
        return MappingHelper.getMapper().map(message, MessageDto.class);
    }

    @Override
    public MessageDto update(MessageDto messageDto) {
        Message message = MappingHelper.getMapper().map(messageDto, Message.class);
        message.setModifiedBy(message.getSender().getUserName());
        logger.info("{} update by {}", message, message.getSender());
        return MappingHelper.getMapper().map(messageRepository.save(message), MessageDto.class);
    }

    @Override
    public MessageDto delete(MessageDto messageDto) {
        Message message = MappingHelper.getMapper().map(messageDto, Message.class);
        message.setModifiedBy(message.getSender().getUserName());
        message.setEntityStatus(EntityStatus.PASSIVE);
        logger.info("{} delete by {}", message, message.getSender());
        return MappingHelper.getMapper().map(messageRepository.save(message), MessageDto.class);
    }

    @Override
    public List<MessageDto> listAll() {
        return messageRepository.findAllByEntityStatus(EntityStatus.ACTIVE).stream().map(
                message -> MappingHelper.getMapper().map(message, MessageDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> listAllByRoom(String roomId) {
        return messageRepository.findAllByRoomId(roomId, EntityStatus.ACTIVE).stream().map(
                message -> MappingHelper.getMapper().map(message, MessageDto.class)
        ).collect(Collectors.toList());
    }
}
