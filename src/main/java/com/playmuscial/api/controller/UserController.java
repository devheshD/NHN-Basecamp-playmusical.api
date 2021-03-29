package com.playmuscial.api.controller;

import com.playmuscial.api.dto.UserDTO;
import com.playmuscial.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/userIdChk")
    public boolean userIdChkPost(@RequestBody String id) {
        return userService.idCheck(id);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/dooray")
    public ResponseEntity<String> getDoorayUrl(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.findById(userDTO.getId()).getUrl(), HttpStatus.OK);
    }

    @PostMapping("/accesslogin")
    public ResponseEntity<String> auth(@RequestBody String userData) {
        return userService.initToken(userData);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userData) {
        return userService.login(userData);
    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody String id) {
        return userService.getToken(id);
    }

}
