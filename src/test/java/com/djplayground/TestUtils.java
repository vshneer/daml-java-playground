package com.djplayground;

import com.daml.extensions.testing.ledger.SandboxManager;

import com.daml.ledger.javaapi.data.*;
import com.daml.ledger.javaapi.data.codegen.Exercised;
import com.daml.ledger.javaapi.data.codegen.Update;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import main.Agreement;
import main.Message;
import main.Proposal;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.daml.extensions.testing.Dsl.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {
    protected static final String ADMIN_PARTY_ID = "ADMIN_PARTY_ID";
    protected static final Identifier DUMMY_IDENTIFIER = new Identifier("", "", "");
    protected static final String DAML_EVENT_ID = "DAML_EVENT_ID";
    protected static final ObjectMapper jsonMapper = new ObjectMapper();
    public static final String SANDBOX_PORT = "6865";
    public static final String SANDBOX_HOST = "localhost";
    protected static final Path DAML_ROOT = Path.of("./src/main/").toAbsolutePath();
    protected static final String TESTS_MODULE = "Test";
    protected static final String SETUP_SCRIPT = "allocateParties";
    protected static final Path RELATIVE_DAR_PATH = Path.of("./src/main/.daml/dist/djplayground-0.0.1.dar").toAbsolutePath();
    protected static final String DISPLAY_NAME_PROPOSER = "proposer";
    protected static final String DISPLAY_NAME_COUNTERPARTY = "counterparty";
    protected static final String DISPLAY_NAME_SENDER = "sender";
    protected static final String DISPLAY_NAME_RECEIVER = "receiver";

    protected static final SandboxManager SANDBOX = new SandboxManager(
            DAML_ROOT,
            Optional.of(TESTS_MODULE),
            Optional.of(SETUP_SCRIPT),
            Optional.of(Integer.parseInt(SANDBOX_PORT)),
            Duration.ofSeconds(30),
            Duration.ofSeconds(10),
            new String[]{},
            RELATIVE_DAR_PATH,
            Optional.empty(),
            (client, channel) -> {
            },
            false);

    protected static Party proposerPartyId;
    protected static Party counterpartyPartyId;

    protected static Party receiverPartyId;
    protected static Party senderPartyId;

    @BeforeAll
    protected static void beforeAll() throws IOException, InterruptedException, TimeoutException {
        SANDBOX.start();
        refreshCommonTestSetUp();
    }

    @AfterAll
    protected static void stopSandbox(){
        SANDBOX.stop();
    }

    private static void refreshCommonTestSetUp() {
        setUpPartyVariables();
    }

    protected static void setUpPartyVariables(){
        proposerPartyId = SANDBOX.getPartyId(DISPLAY_NAME_PROPOSER);
        counterpartyPartyId = SANDBOX.getPartyId(DISPLAY_NAME_COUNTERPARTY);

        senderPartyId   = SANDBOX.getPartyId(DISPLAY_NAME_SENDER);
        receiverPartyId = SANDBOX.getPartyId(DISPLAY_NAME_RECEIVER);
    }

    protected static void produceMessageOnKafka(KafkaCompanion companion, String topic, Object[] payloads) throws IOException {
        List<ProducerRecord<String, String>> records = generateJsonStrRecords(payloads, topic);
        companion.produceStrings().fromRecords(records);
    }

    protected static List<ProducerRecord<String, String>> generateJsonStrRecords(Object[] payloads, String topic) throws JsonProcessingException {
        List<ProducerRecord<String, String>> records = new ArrayList<>();
        for (Object payload : payloads) {
            String kafkaProducerPayload = jsonMapper.writeValueAsString(payload);
            records.add(new ProducerRecord<>(topic, kafkaProducerPayload));
        }
        return records;
    }

    protected static <Cid> void lookUpContract(
            Identifier contractIdentifier,
            Function<String, Cid> cidConstructor,
            Party observer){
            SANDBOX.getLedgerAdapter().getCreatedContractId(
                    observer,
                    contractIdentifier,
                    cidConstructor);
    }

    protected static <Cid> void lookUpContractWithMatcher(
            Identifier contractIdentifier,
            Function<String, Cid> cidConstructor,
            List<DamlRecord> matchers,
            Party observer){
        Assertions.assertTrue(matchers.size() > 0);
        for (DamlRecord record: matchers) {
            SANDBOX.getLedgerAdapter().getCreatedContractId(
                    observer,
                    contractIdentifier,
                    record,
                    cidConstructor);
        }
    }

    protected static CreatedEvent getCreatedEvent(Identifier identifier) {
        var createdEvent = mock(CreatedEvent.class);
        when(createdEvent.getTemplateId()).thenReturn(identifier);
        return createdEvent;
    }

    protected static Proposal.ContractId createProposalContractOnTheLedger() throws InvalidProtocolBufferException {
        DamlRecord record = record(
                field("proposer", proposerPartyId),
                field("counterparty", counterpartyPartyId),
                field("payload", text("PAYLOAD")));
        SANDBOX.getLedgerAdapter().createContract(proposerPartyId, Proposal.TEMPLATE_ID, record);
        Proposal.ContractId proposalContractId = SANDBOX.getLedgerAdapter().getCreatedContractId(counterpartyPartyId, Proposal.TEMPLATE_ID, Proposal.ContractId::new);
        return proposalContractId;
    }

    protected static void exerciseAcceptProposal() throws InvalidProtocolBufferException {
        Proposal.ContractId proposalContractId = createProposalContractOnTheLedger();
        Update<Exercised<Agreement.ContractId>> exerciseAcceptProposalCommand = proposalContractId.exerciseAccept();
        SANDBOX.getLedgerAdapter().exerciseChoice(counterpartyPartyId, (ExerciseCommand) exerciseAcceptProposalCommand.commands().get(0));
    }

    protected static Message.ContractId createMessageContractOnTheLedger() throws InvalidProtocolBufferException {
        DamlRecord record = record(
                field("sender", senderPartyId),
                field("receiver", receiverPartyId),
                field("content", text("CONTENT")));
        SANDBOX.getLedgerAdapter().createContract(senderPartyId, Message.TEMPLATE_ID, record);
        Message.ContractId messageContractId = SANDBOX.getLedgerAdapter().getCreatedContractId(receiverPartyId, Message.TEMPLATE_ID, Message.ContractId::new);
        return messageContractId;
    }
    protected static void exerciseAcceptMessage() throws InvalidProtocolBufferException {
        Message.ContractId messageContractId = createMessageContractOnTheLedger();
        Update<Exercised<Agreement.ContractId>> exerciseAcceptMessageCommand = messageContractId.exerciseAcceptMessage();
        SANDBOX.getLedgerAdapter().exerciseChoice(receiverPartyId, (ExerciseCommand) exerciseAcceptMessageCommand.commands().get(0));
    }
    protected static <T> void kafkaAwaitCompletion(KafkaCompanion companion, String topic, int amountOfRecords){
        assertTrue(amountOfRecords > 0);

        var consumer = companion
                .consumeStrings()
                .withAutoCommit()
                .fromTopics(topic, amountOfRecords);

        consumer.awaitCompletion();
        consumer.getRecords();
    }
    protected void eventually(Runnable code) throws InterruptedException {
        Instant started = Instant.now();
        Function<Duration, Boolean> hasPassed =
                x -> Duration.between(started, Instant.now()).compareTo(x) > 0;
        boolean isSuccessful = false;
        while (!isSuccessful) {
            try {
                code.run();
                isSuccessful = true;
            } catch (Throwable ignore) {
                if (hasPassed.apply(Duration.ofSeconds(30))) {
                    fail("Code did not succeed in time.");
                } else {
                    Thread.sleep(200);
                    isSuccessful = false;
                }
            }
        }
    }
}
