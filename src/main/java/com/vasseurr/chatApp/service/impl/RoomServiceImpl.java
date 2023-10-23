package com.vasseurr.chatApp.service.impl;

import com.vasseurr.chatApp.dto.RoomDto;
import com.vasseurr.chatApp.exception.NoSuchElementFoundException;
import com.vasseurr.chatApp.model.EntityStatus;
import com.vasseurr.chatApp.model.Room;
import com.vasseurr.chatApp.model.User;
import com.vasseurr.chatApp.repository.RoomRepository;
import com.vasseurr.chatApp.service.RoomService;
import com.vasseurr.chatApp.util.MappingHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private static final Logger logger = LogManager.getLogger(RoomServiceImpl.class);

    private RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomDto findById(String id) {
        return MappingHelper.getMapper().map(roomRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementFoundException("No such room exists")), RoomDto.class);
    }

    @Override
    public RoomDto save(RoomDto roomDto) {
        List<Room> userRoomList = roomRepository.findAllRoomByUserIdAndEntityStatus(roomDto.getOnlineUser().getId());

        // for comparing users which they are existing in the room
        roomDto.getUsers().add(roomDto.getOnlineUser());
        Set<User> roomUsers = roomDto.getUsers().stream().map(
                userDto -> MappingHelper.getMapper().map(userDto, User.class)
        ).collect(Collectors.toSet());

        // filter by user list
        userRoomList =  userRoomList.stream().filter(
                room ->
                        // if the sizes are not equal, automatically they are not matching
                        room.getUsers().size() == roomUsers.size()
                                && room.getUsers().stream().allMatch(
                        user -> roomUsers.contains(user)
                )
        ).toList();

        if(!userRoomList.isEmpty()) {
            //return previously created room
            RoomDto tempRoomDto = MappingHelper.getMapper().map(userRoomList.get(0), RoomDto.class);
            tempRoomDto.setOnlineUser(roomDto.getOnlineUser());
            tempRoomDto.setCreatedBefore(true);
            return tempRoomDto;
        }

        // users have not any room history, room is creating
        Room room = Room.builder()
                .users(roomDto.getUsers().stream().map(
                        userDto -> MappingHelper.getMapper().map(userDto, User.class)
                ).collect(Collectors.toSet()))
                .build();
        room.setCreatedBy(roomDto.getOnlineUser().getUserName());
        room.setModifiedBy(roomDto.getOnlineUser().getUserName());
        room = roomRepository.save(room);
        logger.info("{} saved by {}", room, roomDto.getOnlineUser());
        RoomDto tempRoomDto = MappingHelper.getMapper().map(room, RoomDto.class);
        tempRoomDto.setOnlineUser(roomDto.getOnlineUser());
        tempRoomDto.setCreatedBefore(false);
        return tempRoomDto;
    }

    @Override
    public RoomDto update(RoomDto roomDto) {
        Room room = MappingHelper.getMapper().map(roomDto, Room.class);
        room.setModifiedBy(roomDto.getOnlineUser().getUserName());
        logger.info("{} updated by {}", room, roomDto.getOnlineUser());
        room = roomRepository.save(room);
        RoomDto tempRoomDto = MappingHelper.getMapper().map(room, RoomDto.class);
        tempRoomDto.setOnlineUser(roomDto.getOnlineUser());
        return MappingHelper.getMapper().map(tempRoomDto, RoomDto.class);
    }

    @Override
    public RoomDto delete(RoomDto roomDto) {
        Room room = MappingHelper.getMapper().map(roomDto, Room.class);
        room.setModifiedBy(roomDto.getOnlineUser().getUserName());
        room.setEntityStatus(EntityStatus.PASSIVE);
        logger.info("{} deleted by {}", room, roomDto.getOnlineUser());
        room = roomRepository.save(room);
        RoomDto tempRoomDto = MappingHelper.getMapper().map(room, RoomDto.class);
        tempRoomDto.setOnlineUser(roomDto.getOnlineUser());
        return MappingHelper.getMapper().map(tempRoomDto, RoomDto.class);
    }

    @Override
    public List<RoomDto> listAll() {
        return roomRepository.findAllByEntityStatus(EntityStatus.ACTIVE).stream().map(
                room -> MappingHelper.getMapper().map(room, RoomDto.class)
        ).collect(Collectors.toList());
    }

    //todo: think about it
    //todo: job service will create for if the room modified by more than 10 days, automatically entity_status equals 0
}
