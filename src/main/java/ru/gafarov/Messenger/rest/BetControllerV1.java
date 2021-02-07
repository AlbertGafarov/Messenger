package ru.gafarov.Messenger.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gafarov.Messenger.dto.ChangeStatusBetDto;
import ru.gafarov.Messenger.dto.bet.CreateBetDto;
import ru.gafarov.Messenger.dto.bet.ShowBetDto;
import ru.gafarov.Messenger.exception_handling.BetException;
import ru.gafarov.Messenger.exception_handling.MessageIncorrectData;
import ru.gafarov.Messenger.model.Bet;
import ru.gafarov.Messenger.model.User;
import ru.gafarov.Messenger.security.jwt.JwtAuthentificationException;
import ru.gafarov.Messenger.service.BetService;
import ru.gafarov.Messenger.service.MessageService;
import ru.gafarov.Messenger.service.UserService;

@RestController
@RequestMapping("/api/v1/bets")
public class BetControllerV1 {

    @Autowired
    private UserService userService;

    @Autowired
    private BetService betService;

    @Autowired
    private MessageService messageService;

    @PostMapping("")
    public ResponseEntity<ShowBetDto> offerBet(@RequestBody CreateBetDto createBetDto, @RequestHeader("Authorization") String bearerToken){
        String token = bearerToken.substring(7);
        User me = userService.getMe(token);
        Bet bet = betService.save(createBetDto,me);
        ShowBetDto showBetDto = ShowBetDto.fromBet(bet);

        return new ResponseEntity<>(showBetDto, HttpStatus.CREATED);
    }

    @GetMapping("/{betId}")
    public ResponseEntity<ShowBetDto> showBet(@RequestHeader("Authorization") String bearerToken, @PathVariable Long betId) {
        String token = bearerToken.substring(7);
        User me = userService.getMe(token);
        Bet bet = betService.showBet(betId, me);
        return new ResponseEntity<>(ShowBetDto.fromBet(bet),HttpStatus.OK);
    }
    @PutMapping("/{betId}")
    public ResponseEntity<ShowBetDto> changeBetStatus(@RequestHeader("Authorization") String bearerToken, @PathVariable Long betId
            , @RequestBody ChangeStatusBetDto changeStatusBetDto) {
        String token = bearerToken.substring(7);
        User me = userService.getMe(token);
        Bet bet = betService.changeBetStatus(changeStatusBetDto, me, betId);
        return new ResponseEntity<>(ShowBetDto.fromBet(bet), HttpStatus.OK);

    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> handleException(BetException exception){
        MessageIncorrectData data = new MessageIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
    /*@ExceptionHandler
    public ResponseEntity<MessageIncorrectData> handleException(Exception exception){
        MessageIncorrectData data = new MessageIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData>handleException(JwtAuthentificationException exception){
        MessageIncorrectData data = new MessageIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }
}
