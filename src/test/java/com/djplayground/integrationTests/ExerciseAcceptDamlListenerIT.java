package com.djplayground.integrationTests;

import com.djplayground.CustomTestProfile;
import com.djplayground.TestUtils;
import com.djplayground.damlClient.listeners.exercise.AcceptProposalDamlListener;
import com.djplayground.damlClient.partyManagement.PartyPlainTextListReader;
import com.google.protobuf.InvalidProtocolBufferException;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static com.djplayground.CustomTestProfile.EVENTID_OUTPUT_TOPIC;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestProfile(CustomTestProfile.class)
public class ExerciseAcceptDamlListenerIT extends TestUtils {
    @InjectKafkaCompanion
    KafkaCompanion companion;
    @Inject
    AcceptProposalDamlListener acceptProposalDamlListener;

    @BeforeAll
    protected static void setup() {
        PartyPlainTextListReader reader = Mockito.mock(PartyPlainTextListReader.class);

        List<String> listOfMockedParty = Collections.singletonList(counterpartyPartyId.getValue());

        when(reader.getParties()).thenReturn(listOfMockedParty);

        QuarkusMock.installMockForType(reader, PartyPlainTextListReader.class);
    }

    @Test
    void WHEN_accept_proposal_exercise_on_ledger_THEN_to_be_implemented() throws InvalidProtocolBufferException, InterruptedException {
        exerciseAcceptProposal();
        eventually(() -> kafkaAwaitCompletion(
                companion,
                EVENTID_OUTPUT_TOPIC,
                1
                ));
    }

}
