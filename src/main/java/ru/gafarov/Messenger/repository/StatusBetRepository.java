package ru.gafarov.Messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gafarov.Messenger.model.StatusBet;

public interface StatusBetRepository extends JpaRepository<StatusBet, Long> {
}
