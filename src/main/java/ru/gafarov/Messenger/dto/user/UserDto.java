package ru.gafarov.Messenger.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.gafarov.Messenger.model.User;

import java.util.Comparator;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto implements Comparable<UserDto> {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }

    public static UserDto fromUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    @Override
    public int compareTo(UserDto anotherUser) {
        return this.id.compareTo(anotherUser.id);
    }

    public static class UserDtoComparator implements Comparator<UserDto> {
        @Override
        public int compare(UserDto user1, UserDto user2) {
            int res = user1.getLastName().compareTo(user2.lastName);
            if (res == 0) {
                res = user1.getFirstName().compareTo(user2.firstName);
                if (res == 0) {
                    res = user1.getUsername().compareTo(user2.username);
                }
            }
            return res;
        }
    }
}