package ru.gafarov.Messenger.dto;

import lombok.Data;
import ru.gafarov.Messenger.model.BetStatusEnum;

@Data
public class ChangeStatusBetDto {
    private BetStatusEnum newBetStatus;
}
