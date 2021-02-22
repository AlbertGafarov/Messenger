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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageControllerV1 {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody SendMessageDto sendMessageDto, @RequestHeader(value = "Authorization") String bearerToken){
        User me = userService.findMe(bearerToken);
        Message message = messageService.sendMessage(sendMessageDto,me);
        return new ResponseEntity<>(MessageDto.fromMessage(message), HttpStatus.CREATED);
    }

    @GetMapping("/with/{somebodyId}")
    public ResponseEntity<List<MessageDto>> showCorrespondenceWithSomebody(@PathVariable Long somebodyId
            , @RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7);
        Long id = userService.getMyId(token);
        List<Message> correspondence = messageService.showCorrespondenceWithSomebody(id,somebodyId);

        List<MessageDto> correspondenceDto = correspondence.stream()
                .map(MessageDto::fromMessage)
                .sorted()
                .collect(Collectors.toList());

        return new ResponseEntity<>(correspondenceDto, HttpStatus.OK);
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
