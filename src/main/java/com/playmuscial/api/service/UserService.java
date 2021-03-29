package com.playmuscial.api.service;

import com.playmuscial.api.dto.UserDTO;
import com.playmuscial.api.entity.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface UserService {

    boolean idCheck(String id);

    User findById(String id);

    boolean register(UserDTO userDTO);

    ResponseEntity<String> initToken(String userData);

    ResponseEntity<String> login(UserDTO userData);

    ResponseEntity<String> getToken(String id);

    default Map<String, Object> dtoToEntity(UserDTO userDTO) {
        Map<String, Object> entityMap = new HashMap<>();
        User user = User.builder().id(userDTO.getId()).name(userDTO.getName())
            .password(userDTO.getPassword()).url(userDTO.getUrl()).build();

        entityMap.put("user", user);
        return entityMap;
    }
}
