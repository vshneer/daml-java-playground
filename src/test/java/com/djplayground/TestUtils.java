package com.djplayground;

import com.daml.extensions.testing.ledger.SandboxManager;

import com.daml.ledger.javaapi.data.Identifier;
import com.daml.ledger.javaapi.data.Party;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class TestUtils {
    public static final String SANDBOX_PORT = "6865";
    public static final String SANDBOX_HOST = "localhost";
    protected static final Path DAML_ROOT = Path.of("./src/main/").toAbsolutePath();
    protected static final String TESTS_MODULE = "Test";
    protected static final String SETUP_SCRIPT = "allocateParties";
    protected static final Path RELATIVE_DAR_PATH = Path.of("./src/main/.daml/dist/djplayground-0.0.1.dar").toAbsolutePath();
    protected static final String DISPLAY_NAME_PROPOSER = "proposer";
    protected static final String DISPLAY_NAME_COUNTERPARTY = "counterparty";
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
}
