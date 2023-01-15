package com.djplayground.conversion.kafka2daml;

import com.djplayground.kafkaClient.message.KafkaMessageMessage;
import main.Message;
import main.Proposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;


public class ToDamlMessageTranslation implements Function<KafkaMessageMessage, Message> {

    private static final Logger logger = LoggerFactory.getLogger(ToDamlMessageTranslation.class);


    public ToDamlMessageTranslation() {
        logger.info("Created ToDamlMessageTranslation");
    }

    @Override
    public Message apply(KafkaMessageMessage kafkaMessageMessage) {
        var receiverId = kafkaMessageMessage.getReceiverPartyId();
        var senderId = kafkaMessageMessage.getSenderPartyId();
        var content = kafkaMessageMessage.getContent();
        return new Message(senderId, receiverId, content);
    }
}

