package com.vasseurr.chatApp.controller;

import com.vasseurr.chatApp.dto.RoomDto;
import com.vasseurr.chatApp.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/room", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(value = "/find")
    public ResponseEntity<RoomDto> find(@RequestParam String roomId) {
        return new ResponseEntity<>(roomService.findById(roomId), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<RoomDto> save(@RequestBody RoomDto roomDto) {
        return new ResponseEntity<>(roomService.save(roomDto), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<RoomDto> update(@RequestBody RoomDto roomDto) {
        return new ResponseEntity<>(roomService.update(roomDto), HttpStatus.OK);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<RoomDto> delete(@RequestBody RoomDto roomDto) {
        return new ResponseEntity<>(roomService.delete(roomDto), HttpStatus.OK);

    }

    @GetMapping(value = "/listAll")
    public ResponseEntity<List<RoomDto>> listAll() {
        return new ResponseEntity<>(roomService.listAll(), HttpStatus.OK);
    }
}
