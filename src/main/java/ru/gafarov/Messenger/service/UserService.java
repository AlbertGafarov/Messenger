package ru.gafarov.Messenger.service;

import ru.gafarov.Messenger.model.User;

import java.util.List;

public interface UserService {
    User register(User user);
    List<User> getAll();
    User findByUsername(String username);
    User findById(Long id);
    void delete(Long id);
    Long getMyId(String token);
    User getMe(String token);
    List<User> searchPeople(String partOfName);
}
