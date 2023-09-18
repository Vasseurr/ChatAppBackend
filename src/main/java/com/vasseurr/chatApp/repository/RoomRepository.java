package com.vasseurr.chatApp.repository;

import com.vasseurr.chatApp.model.EntityStatus;
import com.vasseurr.chatApp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    List<Room> findAllByEntityStatus(EntityStatus entityStatus);

}
