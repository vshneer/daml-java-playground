package com.djplayground.damlClient.parameters;
import lombok.Data;
import main.Message;

@Data
public class AcceptMessageArguments {
    private final String receiverpartyId;
    private final Message.ContractId messageCid;
}

