package ru.gafarov.Messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gafarov.Messenger.model.Message;


import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndDestinationIdOrDestinationIdAndSenderId(Long sender_id, Long destination_id,
                                                                           Long destination_id_too, Long sender_id_too);
}