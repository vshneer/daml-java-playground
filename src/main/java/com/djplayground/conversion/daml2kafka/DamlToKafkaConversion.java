package com.djplayground.conversion.daml2kafka;

import com.daml.ledger.javaapi.data.ExercisedEvent;

/**
 * add default method throwing unsupported exception as each implementation implements either createEventToKafka or exerciseEventToKafka
 */


public interface DamlToKafkaConversion<O> {
    default O exercisedEventToKafka(ExercisedEvent event) {
        // default throw unsupportedException
        throw new UnsupportedOperationException();
    }
}
