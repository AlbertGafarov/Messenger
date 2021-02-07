package ru.gafarov.Messenger.dto.bet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.gafarov.Messenger.model.*;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBetDto {

    private Long reasonMessageId;
    private String definition;
    private String wager;
    private Date finishDate;

    public Bet toBet() {
        Bet bet = new Bet();
        bet.setDefinition(definition);
        bet.setWager(wager);
        bet.setFinishDate(finishDate);
        return bet;
    }
}
