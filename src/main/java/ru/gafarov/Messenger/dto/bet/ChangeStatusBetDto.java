package ru.gafarov.Messenger.dto.bet;

import lombok.Data;
import ru.gafarov.Messenger.model.BetStatusEnum;

@Data
public class ChangeStatusBetDto {
    private BetStatusEnum newBetStatus;
}
