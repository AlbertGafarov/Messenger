package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gafarov.Messenger.dto.UserDto;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.service.UserService;

@RestController
@RequestMapping(value = "/api/v1")
public class RegisterRestControllerV1 {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto){
        System.out.println("хочу зарегистрироваться");
        User user = userDto.toUser();
        User registeredUser = userService.register(user);
        userDto = UserDto.fromUser(registeredUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}