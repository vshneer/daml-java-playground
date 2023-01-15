package com.djplayground;

import com.djplayground.damlClient.implementation.PlaygroundDamlClient;
import io.quarkus.test.junit.QuarkusTest;
import main.Message;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CreateMessageTest extends TestUtils {

    PlaygroundDamlClient damlClient;

    @Test
    void whenCreateCallCreateMessageThenMessageContractCreatedOnLedger(){
        damlClient = new PlaygroundDamlClient(SANDBOX_HOST, Integer.parseInt(SANDBOX_PORT));
        Message message = new Message(senderPartyId.getValue(), receiverPartyId.getValue(), "payload example");
        damlClient.createMessageContract(message);
        lookUpMessageContract();
    }

    private void lookUpMessageContract(){
        lookUpContract(
                Message.TEMPLATE_ID,
                Message.ContractId::new,
                senderPartyId
        );
    }

}
