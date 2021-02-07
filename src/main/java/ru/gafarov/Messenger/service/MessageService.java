package ru.gafarov.Messenger.service;

import ru.gafarov.Messenger.model.Message;
import ru.gafarov.Messenger.model.User;

import java.util.List;

public interface MessageService {
    Message sendMessage(Message message);
    List<Message> showCorrespondenceWithSomebody(Long somebodyId, Long myId);

    Message readMessage(Long id, Long myId);

    Message readMessage(Long id, User me);

    User getInterlocutor(Long messageId, User initiator);
}
