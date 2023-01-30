package com.djplayground.messageprocessing.daml;

import com.daml.ledger.javaapi.data.ExercisedEvent;
import com.djplayground.messageprocessing.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DamlAcceptProposalChoiceExerciseProcessor extends MessageProcessor<ExercisedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DamlAcceptProposalChoiceExerciseProcessor.class);


    public DamlAcceptProposalChoiceExerciseProcessor() {
        logger.info("Created DamlAcceptProposalChoiceExerciseProcessor");
    }

    @Override
    public void publish(ExercisedEvent msg) {
        logger.info("DamlFinalizeRejectSettlementChoiceExerciseProcessor about to publish message {}", msg);
    }
}
