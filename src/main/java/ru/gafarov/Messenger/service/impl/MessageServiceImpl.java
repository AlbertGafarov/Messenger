package ru.gafarov.Messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gafarov.Messenger.model.Message;
import ru.gafarov.Messenger.model.Status;
import ru.gafarov.Messenger.repository.MessageRepository;
import ru.gafarov.Messenger.service.MessageService;
import ru.gafarov.Messenger.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Override
    public Message sendMessage(Message message) {
        message.setCreated(new Date());
        message.setStatus(Status.NOT_READ);
        messageRepository.save(message);
        return message;
    }

    @Override
    public List<Message> showСorrespondenceWithSomebody(Long somebodyId, Long myId) {
       List<Message> correspondence = messageRepository.findBySenderIdAndDestinationIdOrDestinationIdAndSenderId(somebodyId,myId,somebodyId,myId);

        return correspondence;
    }

    @Override
    public Message readMessage(Long id, Long myId) {
        Message message = null;
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            message = optionalMessage.get();
            if (message.getSenderId() == myId || message.getDestinationId() == myId) {
                if (message.getReadDate() == null) {
                    message.setReadDate(new Date());
                    message.setStatus(Status.READ);
                    messageRepository.save(message);
                }
                return message;
            }
        }
        return null;
    }
}
