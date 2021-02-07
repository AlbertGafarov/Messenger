package ru.gafarov.Messenger.model;

import lombok.Data;

import javax.persistence.*;

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
}
