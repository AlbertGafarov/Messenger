package ru.gafarov.Messenger.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.gafarov.Messenger.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserRegisterDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username.trim());
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setEmail(email.trim());
        user.setPassword(password.trim());
        return user;
    }
}