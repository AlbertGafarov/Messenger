package ru.gafarov.Messenger.rest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.dto.UserDto;
import ru.gafarov.Messenger.dto.UserRegisterDto;
import ru.gafarov.Messenger.exception_handling.MessageIncorrectData;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.service.UserService;

@RestController
@RequestMapping(value = "/api/v1")
public class RegisterRestControllerV1 {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRegisterDto userRegisterDto){
        User user = userRegisterDto.toUser();
        User registeredUser = userService.register(user);
        UserDto userDto = UserDto.fromUser(registeredUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData>handleException(ConstraintViolationException exception){
        MessageIncorrectData data = new MessageIncorrectData();
        data.setInfo(exception.getSQLException().getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
}
