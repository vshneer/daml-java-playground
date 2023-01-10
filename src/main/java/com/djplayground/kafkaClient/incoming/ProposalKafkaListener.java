package com.djplayground.kafkaClient.incoming;


import com.djplayground.kafkaClient.message.KafkaMessageProposal;
import com.djplayground.messageprocessing.MessageProcessor;
import com.djplayground.messageprocessing.kafka.KafkaMessageProcessorProposal;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

public class ProposalKafkaListener {

    private static final Logger logger = LoggerFactory.getLogger(ProposalKafkaListener.class);
    MessageProcessor<KafkaMessageProposal> processor;

    public ProposalKafkaListener(KafkaMessageProcessorProposal processor) {
        logger.info("Created KafkaMessageProcessorProposal with processor {}", processor);
        this.processor = processor;
    }

    @Transactional
    @Incoming("proposal-message-in")
    public void acceptProposalMessage(KafkaMessageProposal messagePayload) {
        processor.accept(messagePayload);
    }

}