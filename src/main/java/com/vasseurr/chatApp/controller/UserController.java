package com.vasseurr.chatApp.controller;

import com.vasseurr.chatApp.dto.UserDto;
import com.vasseurr.chatApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/find")
    public ResponseEntity<UserDto> find(@RequestParam String userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.update(userDto), HttpStatus.OK);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<UserDto> delete(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.delete(userDto), HttpStatus.OK);

    }

    @GetMapping(value = "/listAll")
    public ResponseEntity<List<UserDto>> listAll() {
        return new ResponseEntity<>(userService.listAll(), HttpStatus.OK);
    }
}
