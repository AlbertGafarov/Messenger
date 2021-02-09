package ru.gafarov.Messenger.service;

import ru.gafarov.Messenger.dto.bet.ChangeStatusBetDto;
import ru.gafarov.Messenger.dto.bet.CreateBetDto;
import ru.gafarov.Messenger.model.Bet;
import ru.gafarov.Messenger.model.User;

public interface BetService {
    void save(Bet bet);
    Bet save(CreateBetDto createBetDto, User initiator);

    Bet showBet(Long betId, User me);

    Bet changeBetStatus(ChangeStatusBetDto changeStatusBetDto, User me, Long id);
}
