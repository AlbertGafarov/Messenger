package ru.gafarov.Messenger.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.gafarov.Messenger.model.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortUserDto {

   /* @Autowired
    private UserService userService;*/

    private Long id;
    private String username;

    /*public User toUser(){
        User user = userService.findById(id);
        return user;
    }*/

    public static ShortUserDto fromUser(User user){
        ShortUserDto shortUserDto = new ShortUserDto();
        shortUserDto.setId(user.getId());
        shortUserDto.setUsername(user.getUsername());

        return shortUserDto;
    }

}
