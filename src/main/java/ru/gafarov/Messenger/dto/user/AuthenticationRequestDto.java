package ru.gafarov.Messenger.dto.user;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String username;
    private String password;
}
