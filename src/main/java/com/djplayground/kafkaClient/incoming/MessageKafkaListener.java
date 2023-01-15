package com.djplayground.kafkaClient.incoming;


import com.djplayground.kafkaClient.message.KafkaMessageMessage;
import com.djplayground.messageprocessing.MessageProcessor;
import com.djplayground.messageprocessing.kafka.KafkaMessageProcessorMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

public class MessageKafkaListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageKafkaListener.class);
    MessageProcessor<KafkaMessageMessage> processor;

    public MessageKafkaListener(KafkaMessageProcessorMessage processor) {
        logger.info("Created KafkaMessageProcessorMessage with processor {}", processor);
        this.processor = processor;
    }

    @Transactional
    @Incoming("message-message-in")
    public void acceptProposalMessage(KafkaMessageMessage messageContent) {
        processor.accept(messageContent);
    }

}