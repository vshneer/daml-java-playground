package com.djplayground;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

import static com.djplayground.TestUtils.SANDBOX_PORT;

public class CustomTestProfile implements QuarkusTestProfile {

    public static final String PROPOSAL_INPUT_TOPIC = "proposal-in";
    // TIP add your input topic here

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "daml.ledger.port", SANDBOX_PORT,
                "mp.messaging.incoming.proposal-message-in.topic", PROPOSAL_INPUT_TOPIC
                // TIP override config with you topic like you see above
        );

    }
}
