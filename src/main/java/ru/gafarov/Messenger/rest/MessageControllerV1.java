package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.model.Message;
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
        System.out.println(token+" ----------- "+ id );
        messageService.sendMessage(message);
        System.out.println(message);

        return message;
    }

    @GetMapping("/{myId}/{somebodyId}")
    public List<Message> showСorrespondenceWithSomebody(@PathVariable Long myId, @PathVariable Long somebodyId){
        List<Message> correspondence = messageService.showСorrespondenceWithSomebody(myId,somebodyId);
        return correspondence;
    }

    @GetMapping("/{id}")
    public Message readMessage(@PathVariable Long id){
        return messageService.readMessage(id);
    }
}
