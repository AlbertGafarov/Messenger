package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.dto.UserDto;
import ru.gafarov.Messenger.dto.UserRegisterDto;
import ru.gafarov.Messenger.exception_handling.MessageIncorrectData;
import ru.gafarov.Messenger.exception_handling.NoSuchPeopleException;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/userinfo")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7);
        Long myId = userService.getMyId(token);
        User user = userService.findById(myId);
        if (user==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping("/change_info")
    public ResponseEntity<UserDto> update(@RequestBody UserRegisterDto userRegisterDto, @RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7);
        Long id = userService.getMyId(token);
        User user = userRegisterDto.toUser();
        user.setId(id);
        User registeredUser = userService.register(user);
        UserDto userDto = UserDto.fromUser(registeredUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/search_people/{partOfName}")
    public ResponseEntity<List<UserDto>> searchPeople(@PathVariable String partOfName, @RequestHeader(value = "Authorization") String bearerToken){
        if (partOfName.length()<3){
            throw new NoSuchPeopleException("You entered part of name too short. Please enter more than 2 symbols");
        }
        if (partOfName.length()>15){
            throw new NoSuchPeopleException("Yoy entered part of too long. Please enter less than 15 symbols");
        }
        List<User> listOfPeople = userService.searchPeople(partOfName);
        List<UserDto> listOfPeopleDto = new ArrayList<>();
        if (listOfPeople.isEmpty()){
            throw new NoSuchPeopleException("There is no people like: " + partOfName);
        }
        for (User user:listOfPeople) {
            UserDto userDto = UserDto.fromUser(user);
             listOfPeopleDto.add(userDto);
        }
        return new ResponseEntity<>(listOfPeopleDto, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData>handleException(NoSuchPeopleException exception){
        MessageIncorrectData message = new MessageIncorrectData();
        message.setInfo(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }



}
