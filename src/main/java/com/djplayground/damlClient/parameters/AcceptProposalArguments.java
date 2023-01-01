package com.djplayground.damlClient.parameters;
import lombok.Data;
import main.Proposal;

@Data
public class AcceptProposalArguments {
    private final String counterpartyId;
    private final Proposal.ContractId proposalCid;
}

