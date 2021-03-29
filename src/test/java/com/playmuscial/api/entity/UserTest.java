package com.playmuscial.api.entity;

import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void userLombokTest() {
        User origin = random(User.class);

        User builder = User.builder()
                .id(origin.getId())
                .password(origin.getPassword())
                .url(origin.getUrl())
                .name(origin.getName())
                .userNo(origin.getUserNo())
                .build();
        User setter = new User();
        setter.setId(origin.getId());
        setter.setPassword(origin.getPassword());
        setter.setName(origin.getName());
        setter.setUrl(origin.getUrl());
        setter.setUserNo(origin.getUserNo());

        assertEquals(origin.getId(), builder.getId());
        assertEquals(origin.getId(), setter.getId());
    }
}