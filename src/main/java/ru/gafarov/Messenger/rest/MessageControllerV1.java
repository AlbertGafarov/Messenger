package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.dto.message.MessageDto;
import ru.gafarov.Messenger.dto.message.SendMessageDto;
import ru.gafarov.Messenger.exception_handling.MessageIncorrectData;
import ru.gafarov.Messenger.exception_handling.NoSuchMessageException;
import ru.gafarov.Messenger.model.Message;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.security.jwt.JwtAuthentificationException;
import ru.gafarov.Messenger.service.MessageService;
import ru.gafarov.Messenger.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageControllerV1 {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public MessageDto sendMessage(@RequestBody SendMessageDto sendMessageDto, @RequestHeader(value = "Authorization") String bearerToken){
        User me = userService.findMe(bearerToken);
        User destination = userService.findById(sendMessageDto.getDestinationId());
        Message message = new Message();
        message.setSender(me);
        message.setDestination(destination);
        message.setTextMessage(sendMessageDto.getTextMessage());
        messageService.sendMessage(message);

        return MessageDto.fromMessage(message);
    }

    @GetMapping("/with/{somebodyId}")
    public List<MessageDto> showCorrespondenceWithSomebody(@PathVariable Long somebodyId
            , @RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7);
        Long id = userService.getMyId(token);
        List<Message> correspondence = messageService.showCorrespondenceWithSomebody(id,somebodyId);
        List<MessageDto> correspondenceDto = new ArrayList<>();

        for (Message message: correspondence) {

            correspondenceDto.add(MessageDto.fromMessage(message));
        }
        return correspondenceDto;
    }

    @GetMapping("/{id}")
    public MessageDto readMessage(@PathVariable Long id, @RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7);
        Long myId = userService.getMyId(token);
        Message message = messageService.readMessage(id, myId);
        if (message==null){
            throw new NoSuchMessageException("You don't have message with ID:" +id);
           }
        return MessageDto.fromMessage(message);
    }
    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> handleException(NoSuchMessageException exception){
        MessageIncorrectData data = new MessageIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> handleException(Exception exception){
        MessageIncorrectData data = new MessageIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData>handleException(JwtAuthentificationException exception){
        MessageIncorrectData data = new MessageIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }


}
