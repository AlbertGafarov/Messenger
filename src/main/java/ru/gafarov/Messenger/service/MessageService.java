package ru.gafarov.Messenger.service;

import ru.gafarov.Messenger.dto.message.SendMessageDto;
import ru.gafarov.Messenger.model.Message;
import ru.gafarov.Messenger.model.User;

import java.util.List;

public interface MessageService {

    List<Message> showCorrespondenceWithSomebody(Long somebodyId, Long myId);

    Message readMessage(Long id, Long myId);

    Message readMessage(Long id, User me);

    User getInterlocutor(Long messageId, User initiator);

    Message sendMessage(SendMessageDto sendMessageDto, User me);
}
