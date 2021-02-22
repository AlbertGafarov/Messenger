package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.dto.user.UserDto;
import ru.gafarov.Messenger.dto.user.UserRegisterDto;
import ru.gafarov.Messenger.exception_handling.MessageIncorrectData;
import ru.gafarov.Messenger.exception_handling.NoSuchPeopleException;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/userinfo")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader(value = "Authorization") String bearerToken) {
        User user = userService.findMe(bearerToken);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/change_info")
    public ResponseEntity<UserDto> update(@RequestBody UserRegisterDto userRegisterDto, @RequestHeader(value = "Authorization") String bearerToken){
        Long id = userService.getMyId(bearerToken.substring(7));
        User user = userRegisterDto.toUser();
        user.setId(id);
        User registeredUser = userService.register(user);
        return new ResponseEntity<>(UserDto.fromUser(registeredUser), HttpStatus.OK);
    }

    @GetMapping("/search_people/{partOfName}")
    public ResponseEntity<List<UserDto>> searchPeople(@PathVariable String partOfName, @RequestHeader(value = "Authorization") String bearerToken) {
        if (partOfName.length() < 3) {
            throw new NoSuchPeopleException("You entered part of name too short. Please enter more than 2 symbols");
        }
        if (partOfName.length() > 15) {
            throw new NoSuchPeopleException("Yoy entered part of too long. Please enter less than 15 symbols");
        }
        User me = userService.findMe(bearerToken);
        List<User> listOfPeople = userService.searchPeople(partOfName);

        if (listOfPeople.isEmpty()) {
            throw new NoSuchPeopleException("There is no people like: " + partOfName);
        }

        List<UserDto> listOfPeopleDto = listOfPeople.stream().map(u -> UserDto.fromUser(u))
                .sorted(new UserDto.UserDtoComparator())
                .collect(Collectors.toList());
        listOfPeopleDto.removeIf(userDto -> userDto.equals(UserDto.fromUser(me)));

        return new ResponseEntity<>(listOfPeopleDto, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData>handleException(NoSuchPeopleException exception){
        MessageIncorrectData message = new MessageIncorrectData();
        message.setInfo(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }



}
