package ru.gafarov.Messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gafarov.Messenger.model.Message;
import ru.gafarov.Messenger.model.Status;
import ru.gafarov.Messenger.model.User;
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
    public List<Message> showCorrespondenceWithSomebody(Long somebodyId, Long myId) {

        return messageRepository.findBySenderIdAndDestinationIdOrDestinationIdAndSenderId(somebodyId,myId,somebodyId,myId);
    }

    @Override
    public Message readMessage(Long id, Long myId) {
        Message message;
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            message = optionalMessage.get();
            if (message.getSender().getId().equals(myId) || message.getDestination().getId().equals(myId)) {
                if (message.getReadDate() == null && message.getDestination().getId().equals(myId)) {
                    message.setReadDate(new Date());
                    message.setStatus(Status.READ);
                    messageRepository.save(message);
                }
                return message;
            }
        }
        return null;
    }

    @Override
    public Message readMessage(Long id, User me){
        Message message;
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()){
            message = optionalMessage.get();
            if (message.getSender().equals(me) || message.getDestination().equals(me)){
                if (message.getReadDate()==null && message.getDestination().equals(me)){
                    message.setReadDate(new Date());
                    message.setStatus(Status.READ);
                    messageRepository.save(message);
                }
                return message;
            }
        }
        return null;
    }

    public User getInterlocutor(Long messageId, User initiator) throws RuntimeException {
        Message message;
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {
            message = optionalMessage.get();
            if (message.getSender().equals(initiator) && !message.getDestination().equals(initiator)) {
                return message.getDestination();
            } else if (!message.getSender().equals(initiator) && message.getDestination().equals(initiator)) {
                return message.getSender();
            }
        }
        return null;
    }
}
