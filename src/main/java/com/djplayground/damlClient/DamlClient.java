package com.djplayground.damlClient;

import com.daml.ledger.javaapi.data.*;
import com.djplayground.damlClient.parameters.AcceptProposalArguments;
import io.reactivex.Flowable;
import main.Message;
import main.Proposal;

import java.util.List;

/*
*   HOMEWORK 1
*   Roni & Avraham
*
*   please do
*   1. Add new daml contract to Main.daml. Feel free to improvise with the fields!
*   2. Run daml code gen command. From gradle.build or from CLI
*   3. Add new abstract method to the interface below. Method should create the contract that you added
*   4. Implement you abstract method within a PlaygroundDamlClient class (implementation folder)
*
*   Next time we will talk about tests:
*   1. How to run Daml Sandbox from Java
*   2. Execute methods that you created
*   3. Observe results in the Navigator
*
*   Have fun! :)
*
* */


public interface DamlClient {
    void createProposalContract(Proposal proposalArguments);

    void exerciseAccept(AcceptProposalArguments acceptProposalArguments);

    String allocateParty(String hint);

    List<User> listUsers();

    User createUser(String userId, String primaryPartyId);

    Flowable<TransactionTree> getTransactionTree(String subscriberParty, LedgerOffset begin);

    Flowable<CreatedEvent> getActiveContracts(TransactionFilter filter);

    void createMessageContract(Message messageArgs);

}
