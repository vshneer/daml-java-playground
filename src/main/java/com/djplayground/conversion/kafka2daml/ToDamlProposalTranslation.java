package com.djplayground.conversion.kafka2daml;

import com.djplayground.kafkaClient.message.KafkaMessageProposal;
import main.Proposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class ToDamlProposalTranslation implements Function<KafkaMessageProposal, Proposal> {

    private static final Logger logger = LoggerFactory.getLogger(ToDamlProposalTranslation.class);


    public ToDamlProposalTranslation() {
        logger.info("Created ToDamlProposalTranslation");
    }

    @Override
    public Proposal apply(KafkaMessageProposal kafkaMessageProposal) {
        var counterpartyId = kafkaMessageProposal.getCounterpartyPartyId();
        var proposerId = kafkaMessageProposal.getProposerPartyId();
        var payload = kafkaMessageProposal.getPayload();
        return new Proposal(proposerId, counterpartyId, payload);
    }
}

