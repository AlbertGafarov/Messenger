package ru.gafarov.Messenger.model;

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

    public StatusBetList() {
    }
}
