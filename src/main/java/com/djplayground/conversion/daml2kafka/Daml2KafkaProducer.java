package com.djplayground.conversion.daml2kafka;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class Daml2KafkaProducer {

    @ApplicationScoped
    @Produces
    public ExerciseEventToKafka getAcceptProposalDamlMessageToKafka() {
        return new ExerciseEventToKafka();
    }

}