package com.djplayground;

import com.daml.ledger.javaapi.data.DamlRecord;
import com.djplayground.kafkaClient.message.KafkaMessageMessage;
import com.djplayground.kafkaClient.message.KafkaMessageProposal;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import main.Proposal;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.daml.extensions.testing.Dsl.*;
import static com.daml.extensions.testing.Dsl.field;
import static com.djplayground.CustomTestProfile.PROPOSAL_INPUT_TOPIC;


/*
*
*  HOMEWORK 4
*  Integration tests! Are you exited? :)
*
*  I would ask you to write an integration test
*  that will
*   - publish Kafka message
*   - check if contract is created on the sandbox ledger
*
*   You will need to create the similar class that we are in right now
*
*   1. Add your kafka topic to the CustomTestProfile and override the config. Follow the TIPs
*   2. Inject Kafka companion and create test function with @Test annotation, like you already did before
*   3. Implement your own createKafkaProposalPayloads for your Kafka message
*   4. Produce Messages on Kafka with existing produceMessageOnKafka function. Load it with payloads that you constructed with a function from step 3
*   5. Implement your version of getProposalRecordMatchers (and getProposalRecordMatcher) with records of your daml template fields.
*   6. And lookUp for your contract on the Sandbox. You will need to create your version of lookUpProposalContractsWithMatchers but for your Template
*
*   Good luck!
*
* */


@QuarkusTest
@TestProfile(CustomTestProfile.class)
@QuarkusTestResource(KafkaCompanionResource.class)
public class CreateProposalIT extends TestUtils{
    @InjectKafkaCompanion
    KafkaCompanion companion;

    @Test
    void WHEN_proposal_message_publish_to_kafka_THEN_proposal_contract_created_on_ledger() throws IOException {
        int numberOfRecords = 1;

        KafkaMessageProposal[] kafkaProducerPayload = createKafkaProposalPayloads(numberOfRecords);
        produceMessageOnKafka(companion, PROPOSAL_INPUT_TOPIC, kafkaProducerPayload);

        lookUpProposalContractsWithMatchers(getProposalRecordMatchers(kafkaProducerPayload));
    }

    private KafkaMessageProposal[] createKafkaProposalPayloads(int numberOfPayloads) {
        KafkaMessageProposal[] payloads = new KafkaMessageProposal[numberOfPayloads];
        Arrays.fill(
                payloads,
                // TIP in your payload creator you will fill payloads with your Kafka Message. Everything else you can keep the same
                // --- substitute this with you Kafka Data Class ---
                new KafkaMessageProposal(
                        RandomStringUtils.random(4, true, false),
                        counterpartyPartyId.getValue(),
                        proposerPartyId.getValue())
                // ----
        );
        return payloads;
    }

    private void lookUpProposalContractsWithMatchers(List<DamlRecord> matchers){
        lookUpContractWithMatcher(
                Proposal.TEMPLATE_ID,
                Proposal.ContractId::new,
                matchers,
                proposerPartyId);
    }

    private List<DamlRecord> getProposalRecordMatchers(KafkaMessageProposal[] kafkaProducerPayloads) {
        return Arrays.stream(kafkaProducerPayloads).map(kafkaProducerPayload ->
                getProposalRecordMatcher(kafkaProducerPayload)).collect(Collectors.toList());
    }

    private DamlRecord getProposalRecordMatcher(KafkaMessageProposal payload) {
        // TIP be sure that you keep the correct order of fields. The same
        return record(
                field("proposer", proposerPartyId),
                field("counterparty", counterpartyPartyId),
                field("payload", text(payload.getPayload())));
    }
}
