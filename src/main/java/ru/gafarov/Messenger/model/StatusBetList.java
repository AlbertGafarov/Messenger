package ru.gafarov.Messenger.model;

import ru.gafarov.Messenger.exception_handling.BetException;

import java.util.ArrayList;

public class StatusBetList extends ArrayList<StatusBet> {

    public boolean contains(StatusBet statusBet) {
        for (StatusBet s: this) {
            if (statusBet.getCurrentBetStatus().equals(s.getCurrentBetStatus())
                    && statusBet.getNewBetStatus().equals(s.getNewBetStatus())){
                return true;
            }
        }
        return false;
    }

    public String getErrorMessage (StatusBet statusBet){
        for (StatusBet s: this) {
            if (statusBet.getCurrentBetStatus().equals(s.getCurrentBetStatus())){
                return s.getMessage();
            }
        }
        throw new BetException("Не нашелся текущий статус в бд");
    }

    public StatusBetList() {
    }
}
