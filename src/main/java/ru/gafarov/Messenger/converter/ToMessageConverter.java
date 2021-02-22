package ru.gafarov.Messenger.converter;

import ru.gafarov.Messenger.dto.message.SendMessageDto;
import ru.gafarov.Messenger.exception_handling.MessageIncorrectData;
import ru.gafarov.Messenger.exception_handling.NoSuchMessageException;
import ru.gafarov.Messenger.model.Message;


public class ToMessageConverter {

    public static Message toMessage (SendMessageDto messageDto){
        Message message = new Message();
        String textMessage = messageDto.getTextMessage().trim();
        if(textMessage.isEmpty()){
            throw new NoSuchMessageException("text message must not be empty");
        }
        message.setTextMessage(textMessage);
        return message;
    }
}
