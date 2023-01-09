package com.djplayground.messageprocessing.kafka;

import com.djplayground.conversion.kafka2daml.ToDamlProposalTranslation;
import com.djplayground.damlClient.DamlClient;
import com.djplayground.damlClient.implementation.PlaygroundDamlClient;
import com.djplayground.kafkaClient.message.KafkaMessageProposal;
import com.djplayground.messageprocessing.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageProcessorProposal extends MessageProcessor<KafkaMessageProposal> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageProcessorProposal.class);
    private final DamlClient damlClient;
    private final ToDamlProposalTranslation toDamlProposalTranslation;

    public KafkaMessageProcessorProposal(DamlClient damlClient, ToDamlProposalTranslation toDamlProposalTranslation) {
        logger.info("Created KafkaMessageProcessorProposal with DamlClient {}, translation {}", damlClient, toDamlProposalTranslation);
        this.damlClient = damlClient;
        this.toDamlProposalTranslation = toDamlProposalTranslation;
    }

    @Override
    public void publish(KafkaMessageProposal messagePayload) {
        logger.info("Received Proposal {}", messagePayload);
        damlClient.createProposalContract(toDamlProposalTranslation.apply(messagePayload));
    }
}
