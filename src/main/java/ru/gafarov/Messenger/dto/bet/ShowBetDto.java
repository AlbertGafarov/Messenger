package ru.gafarov.Messenger.dto.bet;

import lombok.Data;
import ru.gafarov.Messenger.dto.user.ShortUserDto;
import ru.gafarov.Messenger.model.Bet;
import ru.gafarov.Messenger.model.BetStatusEnum;
import ru.gafarov.Messenger.model.Status;

import java.util.Date;

@Data
public class ShowBetDto {

    private Long id;
    private ShortUserDto initiator;
    private String definition;
    private String wager;
    private Date finishDate;
    private ShortUserDto opponent;
    private BetStatusEnum initiatorBetStatusEnum;
    private BetStatusEnum opponentBetStatusEnum;
    private Status status;
    private Date createdDate;



    public static ShowBetDto fromBet(Bet bet){
        ShowBetDto showBetDto = new ShowBetDto();
        if (bet==null){
            return null;
        }
        showBetDto.setId(bet.getId());
        showBetDto.setDefinition(bet.getDefinition());
        showBetDto.setCreatedDate(bet.getCreated());
        showBetDto.setFinishDate(bet.getFinishDate());
        showBetDto.setWager(bet.getWager());
        showBetDto.setInitiatorBetStatusEnum(bet.getInitiatorBetStatus());
        showBetDto.setOpponentBetStatusEnum(bet.getOpponentBetStatus());
        showBetDto.setStatus(bet.getStatus());
        showBetDto.setInitiator(ShortUserDto.fromUser(bet.getInitiator()));
        showBetDto.setOpponent(ShortUserDto.fromUser(bet.getOpponent()));

        return showBetDto;
    }



}
