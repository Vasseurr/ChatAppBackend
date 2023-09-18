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

import java.util.HashSet;
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

   // public RoomDto save(CreateRoomDto createRoomDto) {
    @Override
    public RoomDto save(RoomDto roomDto) {
        Set<User> userSet = new HashSet<>();
        userSet.add(MappingHelper.getMapper().map(roomDto.getOnlineUser(), User.class));
        Room room = Room.builder()
                .users(userSet)
                .build();
        room.setCreatedBy(roomDto.getOnlineUser().getUserName());
        room.setModifiedBy(roomDto.getOnlineUser().getUserName());
        logger.info("{} saved by {}", room, roomDto.getOnlineUser());
        room = roomRepository.save(room);
        RoomDto tempRoomDto = MappingHelper.getMapper().map(room, RoomDto.class);
        tempRoomDto.setOnlineUser(roomDto.getOnlineUser());
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
}
