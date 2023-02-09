package com.djplayground.integrationTests;

import com.daml.ledger.javaapi.data.DamlRecord;
import com.djplayground.CustomTestProfile;
import com.djplayground.TestUtils;
import com.djplayground.kafkaClient.message.KafkaMessageMessage;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import main.Message;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.daml.extensions.testing.Dsl.*;
import static com.djplayground.CustomTestProfile.MESSAGE_INPUT_TOPIC;


@QuarkusTest
@TestProfile(CustomTestProfile.class)
@QuarkusTestResource(KafkaCompanionResource.class)
public class CreateMessageIT extends TestUtils {
    @InjectKafkaCompanion
    KafkaCompanion companion;

    @Test
    void WHEN_message_message_publish_to_kafka_THEN_message_contract_created_on_ledger() throws IOException {
        int numberOfRecords = 1;

        KafkaMessageMessage[] kafkaProducerPayload = createKafkaMessagePayloads(numberOfRecords);

        produceMessageOnKafka(companion, MESSAGE_INPUT_TOPIC, kafkaProducerPayload);

        lookUpMessageContractsWithMatchers(getMessageRecordMatchers(kafkaProducerPayload));
    }

    private KafkaMessageMessage[] createKafkaMessagePayloads(int numberOfPayloads) {
        KafkaMessageMessage[] payloads = new KafkaMessageMessage[numberOfPayloads];
        Arrays.fill(
                payloads,
                // TIP in your payload creator you will fill payloads with your Kafka Message. Everything else you can keep the same
                // --- substitute this with you Kafka Data Class ---
                new KafkaMessageMessage(
                        "content_"+RandomStringUtils.random(4, true, false),
                        receiverPartyId.getValue(),
                        senderPartyId.getValue())
                // ----
        );
        return payloads;
    }

    private void lookUpMessageContractsWithMatchers(List<DamlRecord> matchers){
        lookUpContractWithMatcher(
                Message.TEMPLATE_ID,
                Message.ContractId::new,
                matchers,
                senderPartyId);
    }

    private List<DamlRecord> getMessageRecordMatchers(KafkaMessageMessage[] kafkaProducerPayloads) {
        return Arrays.stream(kafkaProducerPayloads).map(kafkaProducerPayload ->
                getMessageRecordMatcher(kafkaProducerPayload)).collect(Collectors.toList());
    }

    private DamlRecord getMessageRecordMatcher(KafkaMessageMessage payload) {
        // TIP be sure that you keep the correct order of fields. The same
        return record(
                field("senderPartyId", senderPartyId),
                field("receiverPartyId", receiverPartyId),
                field("content", text(payload.getContent())));
    }
}
