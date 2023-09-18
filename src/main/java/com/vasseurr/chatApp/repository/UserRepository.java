package com.vasseurr.chatApp.repository;

import com.vasseurr.chatApp.model.EntityStatus;
import com.vasseurr.chatApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByEntityStatus(EntityStatus entityStatus);

}
