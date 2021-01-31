package ru.gafarov.Messenger.service;

import ru.gafarov.Messenger.model.Message;

import java.util.List;

public interface MessageService {
    public Message sendMessage(Message message);
    public List<Message> showСorrespondenceWithSomebody(Long somebodyId, Long myId);

    public Message readMessage(Long id, Long myId);
}
