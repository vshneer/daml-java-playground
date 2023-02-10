package com.djplayground.messageprocessing;

import com.djplayground.conversion.kafka2daml.ToDamlMessageTranslation;
import com.djplayground.conversion.kafka2daml.ToDamlProposalTranslation;
import com.djplayground.damlClient.DamlClient;
import com.djplayground.messageprocessing.daml.DamlAcceptMessageChoiceExerciseProcessor;
import com.djplayground.messageprocessing.daml.DamlAcceptProposalChoiceExerciseProcessor;
import com.djplayground.messageprocessing.kafka.KafkaMessageProcessorMessage;
import com.djplayground.messageprocessing.kafka.KafkaMessageProcessorProposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class MessageProcessingProducer2 {

    Logger logger = LoggerFactory.getLogger(MessageProcessingProducer2.class);

    @ApplicationScoped
    @Produces
    KafkaMessageProcessorMessage getLedgerAdapterProcessor(DamlClient damlClient,
                                                           ToDamlMessageTranslation translation) {
        logger.info("Created KafkaProcessorMessage with damlClient {}, translation {}", damlClient, translation);
        return new KafkaMessageProcessorMessage(damlClient, translation);
    }

    @ApplicationScoped
    @Produces
    DamlAcceptMessageChoiceExerciseProcessor getAcceptMessageChoiceExerciseProcessor(){
        logger.info("Created DamlAcceptmessageChoiceExerciseProcessor");
        return new DamlAcceptMessageChoiceExerciseProcessor();
    }
}
