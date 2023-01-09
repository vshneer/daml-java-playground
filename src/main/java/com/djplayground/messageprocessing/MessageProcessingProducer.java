package com.djplayground.messageprocessing;

import com.djplayground.conversion.kafka2daml.ToDamlProposalTranslation;
import com.djplayground.damlClient.DamlClient;
import com.djplayground.messageprocessing.kafka.KafkaMessageProcessorProposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class MessageProcessingProducer {

    Logger logger = LoggerFactory.getLogger(MessageProcessingProducer.class);

    @ApplicationScoped
    @Produces
    KafkaMessageProcessorProposal getLedgerAdapterProcessor(DamlClient damlClient,
                                                            ToDamlProposalTranslation translation) {
        logger.info("Created KafkaProcessorProposal with damlClient {}, translation {}", damlClient, translation);
        return new KafkaMessageProcessorProposal(damlClient, translation);
    }

}
