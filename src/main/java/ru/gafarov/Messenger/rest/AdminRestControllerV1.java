package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.dto.user.AdminUserDto;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.service.UserService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRestControllerV1 {
    @Autowired
    private UserService userService;

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);
        if (user==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
