package ru.gafarov.Messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gafarov.Messenger.dto.ChangeStatusBetDto;
import ru.gafarov.Messenger.dto.bet.CreateBetDto;
import ru.gafarov.Messenger.exception_handling.BetException;
import ru.gafarov.Messenger.model.*;
import ru.gafarov.Messenger.repository.BetRepository;
import ru.gafarov.Messenger.repository.MessageRepository;
import ru.gafarov.Messenger.service.BetService;
import ru.gafarov.Messenger.service.MessageService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BetServiceImpl implements BetService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private StatusBetList statusBetList;


    @Override
    public void save(Bet bet) {

        betRepository.save(bet);
    }

    public Bet save(CreateBetDto createBetDto, User initiator) {

        User opponent = messageService.getInterlocutor(createBetDto.getReasonMessageId(), initiator);
        Message message = messageService.readMessage(createBetDto.getReasonMessageId(), initiator);
        if (message.getBet() != null) {
            throw new BetException("This message has one bet yet with id: " + message.getBet().getId());
        }
        Bet bet = createBetDto.toBet();
        bet.setCreated(new Date());
        bet.setInitiator(initiator);
        bet.setReasonMessage(message);
        bet.setOpponent(opponent);
        bet.setStatus(Status.ACTIVE);
        bet.setInitiatorBetStatus(BetStatusEnum.OFFERED);
        bet.setOpponentBetStatus(BetStatusEnum.OFFERED);
        betRepository.save(bet);

        return bet;

    }

    @Override
    public Bet showBet(Long betId, User me) {
        Optional<Bet> optionalBet = betRepository.findById(betId);
        if (optionalBet.isPresent()) {
            Bet bet = optionalBet.get();
            if (bet.getInitiator().equals(me) || bet.getOpponent().equals(me)) {
                return bet;
            }
        }
        throw new BetException("You don't have bet with id: " + betId);

    }

    @Override
    public Bet changeBetStatus(ChangeStatusBetDto changeStatusBetDto, User me, Long id) {
        BetStatusEnum newBetStatus = changeStatusBetDto.getNewBetStatus();
        Optional<Bet> optionalBet = betRepository.findById(id);
        if (optionalBet.isPresent()) {
            Bet bet = optionalBet.get();
            User initiator = bet.getInitiator();
            User opponent = bet.getOpponent();
            BetStatusEnum initiatorBetStatus = bet.getInitiatorBetStatus();
            BetStatusEnum opponentBetStatus = bet.getOpponentBetStatus();
            if (initiator.equals(me)) {
                StatusBet statusBet = new StatusBet(initiatorBetStatus, newBetStatus);
                if (statusBetList.contains(statusBet)) {
                    bet.setInitiatorBetStatus(newBetStatus);
                    bet.setUpdated(new Date());
                    betRepository.save(bet);
                    return bet;
                } else {
                    throw new BetException("can not set status " +  newBetStatus);
                }

  /*              if (initiatorBetStatus.equals(BetStatusEnum.CANCEL) || opponentBetStatus.equals(BetStatusEnum.CANCEL)){
                    throw new BetException("This bet was canceled. Nobody can change the status of the bet");
                } else if (initiatorBetStatus.equals(BetStatusEnum.WAGERPAID) || initiatorBetStatus.equals(BetStatusEnum.WAGERRECIEVED)){
                    throw new BetException("This bet was closed from your side. "+ initiatorBetStatus +" is finish status");
                } else if (initiatorBetStatus.equals(BetStatusEnum.LOSE) && !newBetStatus.equals(BetStatusEnum.WAGERPAID)){
                    throw new BetException("You lost. You can't change the status of the bet on " + newBetStatus);
                } else if (!initiatorBetStatus.equals(BetStatusEnum.OFFERED) && newBetStatus.equals(BetStatusEnum.CANCEL)){
                    throw new BetException("Your the status of bet is " + initiatorBetStatus + ". You can't cancelled the bet. You can cancelled the bet only from OFFERED status");
                } */
            } else if (opponent.equals(me)){
                StatusBet statusBet = new StatusBet(opponentBetStatus, newBetStatus);
                if (statusBetList.contains(statusBet)){
                    bet.setOpponentBetStatus(newBetStatus);
                    bet.setUpdated(new Date());
                    betRepository.save(bet);
                    return bet;
                } else {
                    throw new BetException("can not set status " +  newBetStatus);
                }
            } else {throw new BetException("You don't have bet with id: " + id);}
        } else {
            throw new BetException("You don't have bet with id: " + id);
        }
    }
}