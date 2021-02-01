package ru.gafarov.Messenger.service;

import ru.gafarov.Messenger.model.User;

import java.util.List;

public interface UserService {
    public User register(User user);
    public List<User> getAll();
    public User findByUsername(String username);
    public User findById(Long id);
    public void delete(Long id);
    public Long getMyId(String token);
    public List<User> searchPeople(String partOfName);
}
