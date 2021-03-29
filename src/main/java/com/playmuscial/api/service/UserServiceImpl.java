package com.playmuscial.api.service;


import com.playmuscial.api.datasource.DatabaseSelector;
import com.playmuscial.api.dto.UserDTO;
import com.playmuscial.api.entity.User;
import com.playmuscial.api.repository.UserRepo;
import com.playmuscial.api.util.Hashing;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final DatabaseSelector databaseSelector;

    @Value("${web.server.host}")
    private String URL;

    @Override
    public ResponseEntity<String> initToken(String userData) {
        JSONObject jsonObject = new JSONObject(userData);
        String id = (String) jsonObject.get("id");
        String pw = Hashing.hashingPassword((String) jsonObject.get("password"));
        databaseSelector.setDbIndicator(id);
        if (Optional.ofNullable(userRepo.findByIdAndPassword(id, pw)).isPresent()) {
            User user = userRepo.findByIdAndPassword(id, pw);
            String token = Math.abs(id.hashCode()) % 2 + "Token" + id;
            user.setToken(token);
            userRepo.save(user);
            String url = URL + "user/accesslogin?id=" + id + "&token=" + token;
            String urlString = "{\"url\":\"" + url + "\"}";
            return new ResponseEntity<>(urlString, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> login(UserDTO userData) {
        databaseSelector.setDbIndicator(userData.getId());

        if (Optional.ofNullable(userRepo.findByIdAndToken(userData.getId(), userData.getToken()))
            .isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> getToken(String id) {
        databaseSelector.setDbIndicator(id);

        if (Optional.ofNullable(userRepo.findById(id)).isPresent()) {
            return new ResponseEntity<>(userRepo.findById(id).getToken(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @Override
    public boolean idCheck(String id) {
        databaseSelector.setDbIndicator(id);
        return findById(id) == null;
    }

    @Override
    public boolean register(UserDTO userDTO) {
        log.info(userDTO.toString());
        databaseSelector.setDbIndicator(userDTO.getId());
        String pass = Hashing.hashingPassword(userDTO.getPassword());

        userDTO.setPassword(pass);
        Map<String, Object> entityMap = dtoToEntity(userDTO);
        User user = (User) entityMap.get("user");
        userRepo.save(user);
        return true;
    }

    public User findById(String id) {
        return userRepo.findById(id);
    }
}
