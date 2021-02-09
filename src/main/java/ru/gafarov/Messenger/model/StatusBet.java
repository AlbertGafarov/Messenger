package ru.gafarov.Messenger.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "status")
public class StatusBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "current_bet_status")
    @Enumerated(EnumType.STRING)
    private BetStatusEnum currentBetStatus;

    @Column(name = "new_bet_status")
    @Enumerated(EnumType.STRING)
    private BetStatusEnum newBetStatus;

    @Column(name = "message")
    private String message;

    public StatusBet(BetStatusEnum currentBetStatus, BetStatusEnum newBetStatus) {
        this.currentBetStatus = currentBetStatus;
        this.newBetStatus = newBetStatus;
    }

    public StatusBet() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusBet statusBet = (StatusBet) o;
        return currentBetStatus == statusBet.currentBetStatus && newBetStatus == statusBet.newBetStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentBetStatus, newBetStatus);
    }
}
