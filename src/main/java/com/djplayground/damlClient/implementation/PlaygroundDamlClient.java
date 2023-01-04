package com.djplayground.damlClient.implementation;

import com.daml.ledger.api.v1.admin.PartyManagementServiceGrpc;
import com.daml.ledger.api.v1.admin.PartyManagementServiceGrpc.PartyManagementServiceBlockingStub;
import com.daml.ledger.api.v1.admin.PartyManagementServiceOuterClass;
import com.daml.ledger.javaapi.data.*;
import com.daml.ledger.javaapi.data.codegen.Exercised;
import com.daml.ledger.javaapi.data.codegen.Update;
import com.daml.ledger.rxjava.DamlLedgerClient;
import com.djplayground.damlClient.DamlClient;
import com.djplayground.damlClient.commandId.RandomGenerator;
import com.djplayground.damlClient.parameters.AcceptProposalArguments;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.reactivex.Flowable;
import main.Agreement;
import main.Message;
import main.Proposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class PlaygroundDamlClient implements DamlClient {
    private static final Logger logger = LoggerFactory.getLogger(DamlClient.class);

    private static final RandomGenerator commandIdGenerator = new RandomGenerator();
    private static final String APP_ID = "DAML_JAVA_PLAYGROUND_APP";
    private static final String WORK_ID = "DAML_JAVA_PLAYGROUND";

    private final PartyManagementServiceBlockingStub partyManagementService;
    private final DamlLedgerClient ledger;

    public PlaygroundDamlClient(String host, int port) {
        logger.info("Starting Daml Java Playground Daml Client...");
        this.ledger = DamlLedgerClient.newBuilder(host, port).build();
        ledger.connect();
        logger.info("Daml Java Playground Daml Client connected to host {}, port {}", host, port);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.partyManagementService = PartyManagementServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void createProposalContract(Proposal proposal) {
        logger.info("{} creating Proposal contract {}", proposal.counterparty, proposal.payload);
        ledger.getCommandClient().submitAndWaitForTransactionTree(WORK_ID, APP_ID, commandIdGenerator.generate(), proposal.proposer,
                Collections.singletonList(proposal.create())).blockingGet();
    }

    @Override
    public void exerciseAccept(AcceptProposalArguments acceptProposalArguments) {
        logger.info("Exercising Accept Proposal choice with: {}", acceptProposalArguments);
        Update<Exercised<Agreement.ContractId>> exerciseCreateProposals = acceptProposalArguments.getProposalCid().exerciseAccept();
        ledger.getCommandClient()
                .submitAndWaitForTransactionTree(WORK_ID, APP_ID, commandIdGenerator.generate(), acceptProposalArguments.getCounterpartyId(),
                        Collections.singletonList(exerciseCreateProposals)).blockingGet();
        logger.info("Done exercising Create Proposal choice with: {}", acceptProposalArguments);
    }

    @Override
    public String allocateParty(String hint) {
        logger.info("Allocating party: {}", hint);
        return partyManagementService
                .allocateParty(PartyManagementServiceOuterClass.AllocatePartyRequest.newBuilder()
                        .setPartyIdHint(hint).build())
                .getPartyDetails().getParty();
    }

    @Override
    public List<User> listUsers() {
        return ledger.getUserManagementClient().listUsers().blockingGet().getUsers();
    }

    @Override
    public User createUser(String userId, String primaryPartyId) {
        logger.info("Creating user: {}, {}", userId, primaryPartyId);
        return ledger.getUserManagementClient().createUser(new CreateUserRequest(userId, primaryPartyId))
                .blockingGet().getUser();
    }

    @Override
    public Flowable<TransactionTree> getTransactionTree(String subscriberParty, LedgerOffset offset) {
        return ledger.getTransactionsClient().getTransactionsTrees(offset, new FiltersByParty(
                Collections.singletonMap(subscriberParty, NoFilter.instance)), true);
    }

    @Override
    public Flowable<CreatedEvent> getActiveContracts(TransactionFilter filter) {
        return ledger.getActiveContractSetClient()
                .getActiveContracts(filter, true)
                .map(GetActiveContractsResponse::getCreatedEvents)
                .flatMap(Flowable::fromIterable);
    }

    @Override
    public void createMessageContract(Message message) {
        ledger.getCommandClient().submitAndWaitForTransactionTree(WORK_ID, APP_ID, commandIdGenerator.generate(), message.sender,
                Collections.singletonList(message.create())).blockingGet();
    }
}
