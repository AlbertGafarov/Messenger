package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.dto.UserDto;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/userinfo")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7, bearerToken.length());
        Long myId = userService.getMyId(token);
        User user = userService.findById(myId);
        if (user==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }



}
