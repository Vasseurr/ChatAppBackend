package com.vasseurr.chatApp.repository;

import com.vasseurr.chatApp.model.EntityStatus;
import com.vasseurr.chatApp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    List<Room> findAllByEntityStatus(EntityStatus entityStatus);

    @Query(value = "select * from room where id IN" +
            "(select room_id from room_users where user_id =:userId) and entity_status = 1",
            nativeQuery = true)
    List<Room> findAllRoomByUserIdAndEntityStatus(@Param("userId") String userId);

}
