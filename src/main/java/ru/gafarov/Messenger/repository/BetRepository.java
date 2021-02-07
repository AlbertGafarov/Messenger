package ru.gafarov.Messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gafarov.Messenger.model.Bet;

public interface BetRepository extends JpaRepository<Bet, Long> {
}
