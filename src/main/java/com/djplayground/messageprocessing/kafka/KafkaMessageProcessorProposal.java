package com.djplayground.messageprocessing.kafka;

import com.djplayground.conversion.kafka2daml.ToDamlProposalTranslation;
import com.djplayground.damlClient.DamlClient;
import com.djplayground.kafkaClient.message.KafkaMessageMessage;
import com.djplayground.kafkaClient.message.KafkaMessageProposal;
import com.djplayground.messageprocessing.MessageProcessor;
import main.Proposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 *  HOMEWORK 3
 *
 *  Create Kafka channel to create your daml template
 *
 *  1. Add new incoming listener under kafkaClient/incoming directory
 *  2. Add corresponding Kafka message under kafkaClient/message with all data that you need to create a contract
 *  3. Create new message processor. Like that you can see in the current file. You can copy-paste this file and adjust it with your logic
 *  4. You will need to add new toDamlTranslation. Like you can see in this file: ToDamlProposalTranslation. Copy-paste this class and adjust with logic for your template
 *  5. Add your message processor (from step 3) to the MessageProcessingProducer class
 *  6. Add your translator (from step 4) to Kafka2DamlProducer class
 *  6. Add your topic to the application.properties configuration. I left comment hints on lines 8 and 20
 *  7. Finally turn on your kafka channel in AdapterConfigSourceFactory. Copy-paste line 22 but edit with the name of your topic
 *  8. Now when you run `./gradle clean build` it should pass with all green. Call me if smth goes wrong!
 *
 *  Next time we will run tests through your kafka channel and create a contract with it!
 *  Isn't is cool?
 *  Enjoy!
 *
 *  *  * PS
 *   You can see that complexity increases with each new session. Be brave. We have another 3 advanced topic to cover:
 *      - Daml listeners/subscription (how to react on daml events)
 *      - Kafka submission            (how to post message on Kafka, think about notifications)
 *      - Caching                     (how to write and read to Java runtime memory between flows)
 *  * - -

 * */


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
        Proposal proposal = toDamlProposalTranslation.apply(messagePayload);
        damlClient.createProposalContract(proposal);
    }

}
