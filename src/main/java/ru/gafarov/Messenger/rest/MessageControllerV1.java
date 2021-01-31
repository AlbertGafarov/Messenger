package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.exception_handling.MessageIncorrectData;
import ru.gafarov.Messenger.exception_handling.NoSuchMessageException;
import ru.gafarov.Messenger.model.Message;
import ru.gafarov.Messenger.security.jwt.JwtAuthentificationException;
import ru.gafarov.Messenger.service.MessageService;
import ru.gafarov.Messenger.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageControllerV1 {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public Message sendMessage( @RequestBody Message message, @RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7, bearerToken.length());
        Long id = userService.getMyId(token);
        message.setSenderId(id);
        System.out.println(token+" ----------- "+ id );
        messageService.sendMessage(message);
        System.out.println(message);

        return message;
    }

    @GetMapping("/with/{somebodyId}")
    public List<Message> showСorrespondenceWithSomebody(@PathVariable Long somebodyId
            , @RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7, bearerToken.length());
        Long id = userService.getMyId(token);
        List<Message> correspondence = messageService.showСorrespondenceWithSomebody(id,somebodyId);
        return correspondence;
    }

    @GetMapping("/{id}")
    public Message readMessage(@PathVariable Long id, @RequestHeader(value = "Authorization") String bearerToken){
        String token = bearerToken.substring(7, bearerToken.length());
        Long myId = userService.getMyId(token);
        Message message = messageService.readMessage(id, myId);
        if (message==null){
            throw new NoSuchMessageException("You don\'t have message with ID:"+id);
            //throw new JwtAuthentificationException("JWT token is expired or invalid");
           }
        return message;
    }
    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData>handleException(NoSuchMessageException exception){
        MessageIncorrectData data = new MessageIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData>handleException(Exception exception){
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
