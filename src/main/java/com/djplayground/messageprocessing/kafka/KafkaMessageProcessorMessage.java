package com.djplayground.messageprocessing.kafka;

import com.djplayground.conversion.kafka2daml.ToDamlMessageTranslation;
import com.djplayground.damlClient.DamlClient;
import com.djplayground.kafkaClient.message.KafkaMessageMessage;
import com.djplayground.messageprocessing.MessageProcessor;
import main.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageProcessorMessage extends MessageProcessor<KafkaMessageMessage> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageProcessorMessage.class);
    private final DamlClient damlClient;
    private final ToDamlMessageTranslation toDamlMessageTranslation;

    public KafkaMessageProcessorMessage(DamlClient damlClient, ToDamlMessageTranslation toDamlMessageTranslation) {
        logger.info("Created KafkaMessageProcessorMessage with DamlClient {}, translation {}", damlClient, toDamlMessageTranslation);
        this.damlClient = damlClient;
        this.toDamlMessageTranslation = toDamlMessageTranslation;
    }

    @Override
    public void publish(KafkaMessageMessage messageContent) {
        logger.info("Received Message {}", messageContent);
        Message message = toDamlMessageTranslation.apply(messageContent);
        damlClient.createMessageContract(message);
    }
}
