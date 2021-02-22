package ru.gafarov.Messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gafarov.Messenger.converter.ToBetConverter;
import ru.gafarov.Messenger.dto.bet.ChangeStatusBetDto;
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
    private List<StatusBet> statusBetList;

    @Autowired
    private ToBetConverter toBetConverter;

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
        Bet bet = toBetConverter.toBet(createBetDto);
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
                if (initiatorBetStatus.equals(BetStatusEnum.OFFERED)){
                    throw new BetException("You initiator and status OFFERED. You can only cancel the bet");
                }
                if (statusBetList.contains(statusBet)) {
                    if (statusBet.getNewBetStatus().equals(BetStatusEnum.CANCEL)){
                        bet.setOpponentBetStatus(BetStatusEnum.CANCEL);
                        bet.setStatus(Status.NOT_ACTIVE);
                    }
                    bet.setInitiatorBetStatus(newBetStatus);
                    bet.setUpdated(new Date());
                    betRepository.save(bet);
                    return bet;

                } else {
                    final String[] message = new String[1];
                    statusBetList.forEach(sB -> {
                        if (sB.getCurrentBetStatus().equals(statusBet.getCurrentBetStatus())){
                            message[0] = sB.getMessage();
                        }
                    } );

                    throw new BetException(message[0]);
                }

            } else if (opponent.equals(me)){
                StatusBet statusBet = new StatusBet(opponentBetStatus, newBetStatus);
                if (statusBetList.contains(statusBet)){
                    if (statusBet.getNewBetStatus().equals(BetStatusEnum.CANCEL)){
                        bet.setInitiatorBetStatus(BetStatusEnum.CANCEL);
                        bet.setStatus(Status.NOT_ACTIVE);
                    }
                    if (statusBet.getNewBetStatus().equals(BetStatusEnum.ACCEPTED)){
                        if (new Date().after(bet.getFinishDate())){
                            bet.setInitiatorBetStatus(BetStatusEnum.CANCEL);
                            bet.setOpponentBetStatus(BetStatusEnum.CANCEL);
                            bet.setStatus(Status.NOT_ACTIVE);
                            throw new BetException("Time is up. The bet will change status on CANCEL");
                        }
                        bet.setInitiatorBetStatus(BetStatusEnum.ACCEPTED);
                        bet.setStatus(Status.ACTIVE);
                    }
                    bet.setOpponentBetStatus(newBetStatus);
                    bet.setUpdated(new Date());
                    betRepository.save(bet);
                    return bet;
                } else {
                    throw new BetException(statusBet.getMessage());
                }
            } else {throw new BetException("You don't have bet with id: " + id);}
        } else {
            throw new BetException("You don't have bet with id: " + id);
        }
    }
}