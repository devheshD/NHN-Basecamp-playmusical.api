package com.playmuscial.api.dto;

import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void userDTOLombokTest() {
        UserDTO origin = random(UserDTO.class);

        UserDTO builder = UserDTO.builder()
                .id(origin.getId())
                .password(origin.getPassword())
                .url(origin.getUrl())
                .name(origin.getName())
                .userNo(origin.getUserNo())
                .build();

        UserDTO setter = new UserDTO();
        setter.setId(origin.getId());
        setter.setPassword(origin.getPassword());
        setter.setName(origin.getName());
        setter.setUrl(origin.getUrl());
        setter.setUserNo(origin.getUserNo());

        // assertEquals(origin.toString(), builder.toString());
        // assertEquals(origin.toString(), setter.toString());
    }
}