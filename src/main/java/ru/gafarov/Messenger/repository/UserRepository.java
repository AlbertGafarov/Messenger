package ru.gafarov.Messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gafarov.Messenger.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    @Query(value = "select * from Users u where lower(u.username) like %?1% or lower(u.username) like %?2% or lower(u.username) like %?3%", nativeQuery = true)
    List<User> searchPeople(String partOfName, String partOfNameLowerCyrilic, String partOfNameLowerLatin);
}
