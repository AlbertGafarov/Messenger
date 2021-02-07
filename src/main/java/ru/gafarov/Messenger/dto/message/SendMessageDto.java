package ru.gafarov.Messenger.dto.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageDto {

    private Long destinationId;
    private String textMessage;

}
