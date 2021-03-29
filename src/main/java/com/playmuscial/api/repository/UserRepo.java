package com.playmuscial.api.repository;

import com.playmuscial.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Long> {

    User findByIdAndPassword(String id, String password);

    User findByIdAndToken(String id, String token);

    User findById(String id);

}

