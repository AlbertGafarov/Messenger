package ru.gafarov.Messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gafarov.Messenger.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
