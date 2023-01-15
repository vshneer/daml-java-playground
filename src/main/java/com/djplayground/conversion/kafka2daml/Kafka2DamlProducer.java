package com.djplayground.conversion.kafka2daml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class Kafka2DamlProducer {

    Logger logger = LoggerFactory.getLogger(Kafka2DamlProducer.class);

    @ApplicationScoped
    @Produces
    public ToDamlProposalTranslation getProposalTranslation() {
        logger.info("created ToDamlProposalTranslation");
        return new ToDamlProposalTranslation();
    }

    @ApplicationScoped
    @Produces
    public ToDamlMessageTranslation getMessageTranslation() {
        logger.info("created ToDamlMessageTranslation");
        return new ToDamlMessageTranslation();
    }
}
