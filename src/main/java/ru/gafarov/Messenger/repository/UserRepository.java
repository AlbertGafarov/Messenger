package ru.gafarov.Messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gafarov.Messenger.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String name);
    @Query("select u from User u where u.username like %?1%")
    List<User> searchPeople(String partOfName);
}
