package ru.gafarov.Messenger.dto.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.gafarov.Messenger.dto.bet.ShowBetDto;
import ru.gafarov.Messenger.dto.user.ShortUserDto;
import ru.gafarov.Messenger.model.Bet;
import ru.gafarov.Messenger.model.Message;
import ru.gafarov.Messenger.model.Status;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {

    private Long id;
    private ShortUserDto sender;
    private ShortUserDto destination;
    private Date createdDate;
    private Date readDate;
    private Status status;
    private String textMessage;
    private ShowBetDto bet;

    public static MessageDto fromMessage(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setDestination(ShortUserDto.fromUser(message.getDestination()));
        messageDto.setSender(ShortUserDto.fromUser(message.getSender()));
        messageDto.setCreatedDate(message.getCreated());
        messageDto.setReadDate(message.getReadDate());
        messageDto.setStatus(message.getStatus());
        messageDto.setTextMessage(message.getTextMessage());
        messageDto.setBet(ShowBetDto.fromBet(message.getBet()));

        return messageDto;
    }


}
