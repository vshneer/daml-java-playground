package com.djplayground.messageprocessing.daml;

import com.daml.ledger.javaapi.data.ExercisedEvent;
import com.djplayground.conversion.daml2kafka.ExerciseEventToKafka;
import com.djplayground.kafkaClient.message.KafkaMessageEventId;
import com.djplayground.kafkaClient.outgoing.KafkaSubmitter;
import com.djplayground.messageprocessing.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
*
*   HOMEWORK 6! Zman tas :)
*
*   Today you a going to submit a message to Kafka as a reaction to Exercise event on the ledger.
*   We are going to implement Integration test as well, since most of the coding work already done in predefined classes and producers
*
*   From the previous session you have your MessageProcessor that receives the event message from Daml
*   Like the one that you see in this file. For Roni it would be DamlAcceptMessageChoiceExerciseProcessor
*   Today you are going to extend it with Translation and KafkaSubmitter
*   You can use already predefined translation and submission classes
*   - first add two private fields for you processor: KafkaSubmitter<KafkaMessageEventId> kafkaSubmitter and ExerciseEventToKafka conversion. See lines 47,48 here
*   - initialize them in the constructor: lines 53,54
*   - add their logic to the publish method. See example in the current class publish method. Lines: 60-62
*   - DONE! Our simple conversion takes eventId from the event message and publish it as a Kafka message the "eventid-message-out" topic
*
*   Now it's time to do the test.
*   - you need to add kafkaAwaitCompletion to the test that you built on the previous session
*   - for Roni it would be ExerciseAcceptMessageIT
*   - all you need is to add these lines after the line where you exercise your choice

    eventually(() -> kafkaAwaitCompletion(
        companion,
        EVENTID_OUTPUT_TOPIC,
        1
    ));

*   - see working example in ExerciseAcceptDamlListenerIT
*
*   I strongly suggest to run the test in the debug mode with breakpoints to understand the flow.

* */

public class DamlAcceptProposalChoiceExerciseProcessor extends MessageProcessor<ExercisedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DamlAcceptProposalChoiceExerciseProcessor.class);
    private final KafkaSubmitter<KafkaMessageEventId> kafkaSubmitter;
    private final ExerciseEventToKafka conversion;

    public DamlAcceptProposalChoiceExerciseProcessor(KafkaSubmitter<KafkaMessageEventId> kafkaSubmitter,
                                                     ExerciseEventToKafka conversion) {
        logger.info("Created DamlAcceptProposalChoiceExerciseProcessor");
        this.kafkaSubmitter = kafkaSubmitter;
        this.conversion = conversion;
    }

    @Override
    public void publish(ExercisedEvent msg) {
        logger.info("DamlAcceptProposalChoiceExerciseProcessor is about to publish message {}", msg);
        var converted = conversion.exercisedEventToKafka(msg);
        logger.info("Publishing {} to Kafka", converted);
        kafkaSubmitter.submit(converted);
    }
}