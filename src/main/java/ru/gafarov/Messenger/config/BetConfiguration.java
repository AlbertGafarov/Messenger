package ru.gafarov.Messenger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gafarov.Messenger.model.StatusBet;
import ru.gafarov.Messenger.repository.StatusBetRepository;
import java.util.List;

@Configuration
public class BetConfiguration {

    @Autowired
    StatusBetRepository statusBetRepository;

    @Bean
    public List<StatusBet> statusBetList(){
        return statusBetRepository.findAll();
    }


}
