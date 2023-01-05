package com.djplayground;

import com.djplayground.damlClient.implementation.PlaygroundDamlClient;
import io.quarkus.test.junit.QuarkusTest;
import main.Proposal;
import org.junit.jupiter.api.Test;


/*
*  HOMEWORK 2
*
*  Test the code that you wrote during HOMEWORK 1
*
*  1. Create Test class under current Directory
*  2. Follow the structure of the current class. It should extend TestUtils, don't forget @QuarkusTest and @Test annotation
*  3. You will need to instantiate PlaygroundDamlClient the same way it is done here
*  4. Instantiate contract object using Java bindings class
*  5. Create a contract passing object from step 4 to the damlClient
*  6. Run the test! Using Play button or ./gradlew test --tests "*<name-of-the-test-class-or-function>"
*  7. Inspect the process using Navigator. You need to run the test in the debug mode. See README.md document under this directory for more info
*
*  Enjoy!
*
*  Next time we will talk about Kafka listener integration
*
* */

@QuarkusTest
public class CreateProposalTest extends TestUtils {

    PlaygroundDamlClient damlClient;

    @Test
    void WHEN_create_call_create_proposal_THEN_proposal_contract_created_on_ledger(){
        damlClient = new PlaygroundDamlClient(SANDBOX_HOST, Integer.parseInt(SANDBOX_PORT));
        Proposal proposal = new Proposal(proposerPartyId.getValue(), counterpartyPartyId.getValue(), "payload example");
        damlClient.createProposalContract(proposal);
        lookUpProposalContract();
    }

    private void lookUpProposalContract(){
        lookUpContract(
                Proposal.TEMPLATE_ID,     // Template ID. Comes from Java Codegen
                Proposal.ContractId::new, // Function that creates new ContractId. Comes from Java Codegen
                proposerPartyId           // Stakeholder's partyId of the contract that you are looking for. Comes from TestUtils
        );
    }

}
