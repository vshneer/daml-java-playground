package com.djplayground.damlClient.parameters;
import lombok.Data;
import main.Message;
import main.Proposal;

@Data
public class AcceptMessageArguments {
    private final String receiverpartyId;
    private final Message.ContractId messageCid;
}

