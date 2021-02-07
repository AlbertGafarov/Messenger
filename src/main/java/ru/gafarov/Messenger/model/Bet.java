package ru.gafarov.Messenger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "bets")
public class Bet extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator; // Инициатор спора

    @ManyToOne
    @JoinColumn(name = "opponent_id")
    private User opponent; // Оппонент, которому предложено спорить

    @NonNull
    @Column(name = "definition")
    private String definition; // Утверждение, которое инициатор предлагает оспорить оппоненту, либо согласиться.

    @NonNull
    @Column(name = "wager")
    private String wager; // Награда, которую получит победитель спора

    @NonNull
    @Column(name = "finish_date")
    private Date finishDate; // Дата, после которой будет очевиден результат спора

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "initiator_bet_status")
    private BetStatusEnum initiatorBetStatus; // Статус спора, по мнению инициатора

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "opponent_bet_status")
    private BetStatusEnum opponentBetStatus; // Статус спора, по мнению оппонента

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id")
    private Message reasonMessage; // Сообщение которое было предпоссылкой для спора

    public Bet() {
    }
}
