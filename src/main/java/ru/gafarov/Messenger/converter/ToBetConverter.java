package ru.gafarov.Messenger.converter;

import org.springframework.stereotype.Service;
import ru.gafarov.Messenger.dto.bet.CreateBetDto;
import ru.gafarov.Messenger.exception_handling.BetException;
import ru.gafarov.Messenger.model.Bet;
import java.util.Date;

@Service
public class ToBetConverter {

        public Bet toBet(CreateBetDto createBetDto) {
            if (createBetDto.getFinishDate().before(new Date())){
                throw new BetException("finish date must be after now timestamp");
            }
            Bet bet = new Bet();
            bet.setDefinition(createBetDto.getDefinition());
            bet.setWager(createBetDto.getWager());
            bet.setFinishDate(createBetDto.getFinishDate());
            return bet;
        }
    }

