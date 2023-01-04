package com.djplayground;

import com.djplayground.damlClient.implementation.PlaygroundDamlClient;
import io.quarkus.test.junit.QuarkusTest;
import main.Proposal;
import org.junit.jupiter.api.Test;


@QuarkusTest
public class CreateProposalTest extends TestUtils {

    // TODO Use @Inject
    PlaygroundDamlClient damlClient;

    @Test
    void WHEN_create_call_create_proposal_THEN_proposal_contract_created_on_ledger(){
        damlClient = new PlaygroundDamlClient(SANDBOX_HOST, Integer.parseInt(SANDBOX_PORT));
        Proposal proposal = new Proposal(proposerPartyId.getValue(), counterpartyPartyId.getValue(), "payload example");
        damlClient.createProposalContract(proposal);
        lookUpProposalContract();
    }


    // TODO add matchers to check data
    private void lookUpProposalContract(){
        lookUpContract(
                Proposal.TEMPLATE_ID,
                Proposal.ContractId::new,
                proposerPartyId
        );
    }

}
