package com.djplayground;

import com.daml.ledger.javaapi.data.DamlRecord;
import com.djplayground.kafkaClient.message.KafkaMessageMessage;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import main.Message;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.daml.extensions.testing.Dsl.*;
import static com.djplayground.CustomTestProfile.MESSAGE_INPUT_TOPIC;


@QuarkusTest
@TestProfile(CustomTestProfile.class)
@QuarkusTestResource(KafkaCompanionResource.class)
public class CreateMessageIT extends TestUtils{
    @InjectKafkaCompanion
    KafkaCompanion companion;

    @Test
    void When_Message_Is_Created_And_Validated_On_The_Ledger () throws IOException {
        int numberOfRecords = 1;

        KafkaMessageMessage[] kafkaMessageMessage = createKafkaMessagePayload(numberOfRecords);
        produceMessageOnKafka(companion, MESSAGE_INPUT_TOPIC, kafkaMessageMessage);

        lookUpMessageContractsWithMatchers(getMessageRecordMatchers(kafkaMessageMessage));
    }

    private List<DamlRecord> getMessageRecordMatchers(KafkaMessageMessage[] kafkaMessageMessages) {
        return Arrays.stream(kafkaMessageMessages).map(kafkaMessageMessage ->
                getMessageRecordMatcher(kafkaMessageMessage)).collect(Collectors.toList());
    }

    private DamlRecord getMessageRecordMatcher(KafkaMessageMessage payload) {
        // TIP be sure that you keep the correct order of fields. The same
        return record(
                field("sender", senderPartyId),
                field("receiver", receiverPartyId),
                field("content", text(payload.getContent())));
    }


    private void lookUpMessageContractsWithMatchers(List<DamlRecord> matchers){
        lookUpContractWithMatcher(
                Message.TEMPLATE_ID,
                Message.ContractId::new,
                matchers,
                proposerPartyId);
    }

    private KafkaMessageMessage[] createKafkaMessagePayload(int numberOfPayloads) {
        KafkaMessageMessage[] payloads = new KafkaMessageMessage[numberOfPayloads];
        Arrays.fill(
                payloads,
                new KafkaMessageMessage(
                        RandomStringUtils.random(4, true, false),
                        receiverPartyId.getValue(),
                        senderPartyId.getValue())
        );
        return payloads;
    }


}
