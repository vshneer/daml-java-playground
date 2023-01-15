package com.djplayground.kafkaClient.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaMessageMessage {
    @NotBlank(message = "Content should not be blank")
    private String content;

    @NotBlank(message = "Receiver PartyId")
    private String receiverPartyId;

    @NotBlank(message = "Sender PartyId")
    private String senderPartyId; // signatory
}
