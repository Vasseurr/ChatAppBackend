package com.vasseurr.chatApp.service.impl;

import com.vasseurr.chatApp.dto.UserDto;
import com.vasseurr.chatApp.exception.NoSuchElementFoundException;
import com.vasseurr.chatApp.model.EntityStatus;
import com.vasseurr.chatApp.model.User;
import com.vasseurr.chatApp.repository.UserRepository;
import com.vasseurr.chatApp.service.UserService;
import com.vasseurr.chatApp.util.MappingHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findById(String id) {
        return MappingHelper.getMapper().map(userRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementFoundException("No such user exists")), UserDto.class);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = MappingHelper.getMapper().map(userDto, User.class);
        user.setCreatedBy(user.getUserName());
        user.setModifiedBy(user.getUserName());
        logger.info("{} saved", user);
        return MappingHelper.getMapper().map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = MappingHelper.getMapper().map(userDto, User.class);
        user.setModifiedBy(user.getUserName());
        logger.info("{} updated", user);
        return MappingHelper.getMapper().map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto delete(UserDto userDto) {
        User user = MappingHelper.getMapper().map(userDto, User.class);
        user.setModifiedBy(user.getUserName());
        logger.info("{} deleted", user);
        user.setEntityStatus(EntityStatus.PASSIVE);
        return MappingHelper.getMapper().map(userRepository.save(user), UserDto.class);
    }

    @Override
    public List<UserDto> listAll() {
        return userRepository.findAllByEntityStatus(EntityStatus.ACTIVE).stream().map(
                user -> MappingHelper.getMapper().map(user, UserDto.class)
        ).collect(Collectors.toList());
    }
}
