package com.vasseurr.chatApp.repository;

import com.vasseurr.chatApp.model.EntityStatus;
import com.vasseurr.chatApp.model.Message;
import com.vasseurr.chatApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findAllByEntityStatus(EntityStatus entityStatus);

    @Query("select message FROM Message message where message.room.id =:roomId and message.entityStatus =:entityStatus")
    List<Message> findAllByRoomId(@Param("roomId") String roomId, @Param("entityStatus") EntityStatus entityStatus);

}
