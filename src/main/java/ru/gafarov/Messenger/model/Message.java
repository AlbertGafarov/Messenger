package ru.gafarov.Messenger.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "destination_id")
    private Long destinationId;

    @Column(name = "read_date")
    private Date readDate;

    @Column(name = "text_message")
    private String textMessage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bet_id")
    private Bet bet;

}
