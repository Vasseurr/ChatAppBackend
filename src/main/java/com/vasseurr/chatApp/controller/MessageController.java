package com.vasseurr.chatApp.controller;

import com.vasseurr.chatApp.dto.MessageDto;
import com.vasseurr.chatApp.dto.RoomDto;
import com.vasseurr.chatApp.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/message", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/find")
    public ResponseEntity<MessageDto> find(@RequestParam String messageId) {
        return new ResponseEntity<>(messageService.findById(messageId), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<MessageDto> save(@RequestBody MessageDto messageDto) {
        return new ResponseEntity<>(messageService.save(messageDto), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<MessageDto> update(@RequestBody MessageDto messageDto) {
        return new ResponseEntity<>(messageService.update(messageDto), HttpStatus.OK);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<MessageDto> delete(@RequestBody MessageDto messageDto) {
        return new ResponseEntity<>(messageService.delete(messageDto), HttpStatus.OK);

    }

    @GetMapping(value = "/listAll")
    public ResponseEntity<List<MessageDto>> listAll() {
        return new ResponseEntity<>(messageService.listAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/listAllByRoom")
    public ResponseEntity<List<MessageDto>> listAllByRoom(@RequestParam String roomId) {
        return new ResponseEntity<>(messageService.listAllByRoom(roomId), HttpStatus.OK);
    }
}
