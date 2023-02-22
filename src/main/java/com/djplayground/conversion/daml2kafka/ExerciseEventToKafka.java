package com.djplayground.conversion.daml2kafka;

import com.daml.ledger.javaapi.data.ExercisedEvent;
import com.djplayground.kafkaClient.message.KafkaMessageEventId;

public class ExerciseEventToKafka implements DamlToKafkaConversion<KafkaMessageEventId> {

    public ExerciseEventToKafka() {
    }
    @Override
    public KafkaMessageEventId exercisedEventToKafka(ExercisedEvent event) {
        return new KafkaMessageEventId(event.getEventId());
    }

}