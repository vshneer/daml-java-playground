package com.djplayground.kafkaClient.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaMessageProposal {
    @NotBlank(message = "Payload should not be blank")
    private String payload;
    @NotBlank(message = "Counterparty PartyId")
    private String counterpartyPartyId;
    @NotBlank(message = "Proposer PartyId")
    private String proposerPartyId;
}
