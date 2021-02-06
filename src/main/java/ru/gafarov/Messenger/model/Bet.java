package ru.gafarov.Messenger.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "bets")
public class Bet extends BaseEntity {

    @NonNull
    @Column(name = "initiator_id")
    private Long initiatorId;

    @NonNull
    @Column(name = "opponent_id")
    private Long opponentId;

    @NonNull
    @Column(name = "definition")
    private String definition;

    @NonNull
    @Column(name = "wager")
    private String wager;

    @NonNull
    @Column(name = "finish_date")
    private Date finishDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "initiator_bet_status")
    private BetStatus initiatorBetStatus;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "opponent_bet_status")
    private BetStatus opponentBetStatus;

    /*@OneToOne(mappedBy = "bet")
    private Message reasonMessage;*/

    public Bet() {
    }
}
