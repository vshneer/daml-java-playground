package com.djplayground;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

import static com.djplayground.TestUtils.SANDBOX_PORT;

public class CustomTestProfile implements QuarkusTestProfile {

    public static final String PROPOSAL_INPUT_TOPIC = "proposal-in";
    public static final String MESSAGE_INPUT_TOPIC = "message-in";
    public static final String EVENTID_OUTPUT_TOPIC = "eventid-out";
    // TIP add your input topic here

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "daml.ledger.port", SANDBOX_PORT,
                "mp.messaging.incoming.proposal-message-in.topic", PROPOSAL_INPUT_TOPIC,
                "mp.messaging.incoming.message-message-in.topic", MESSAGE_INPUT_TOPIC,
                "mp.messaging.outgoing.eventid-message-out.topic", EVENTID_OUTPUT_TOPIC
                // TIP override config with you topic like you see above
        );

    }
}
