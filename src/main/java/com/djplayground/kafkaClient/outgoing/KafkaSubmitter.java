package com.djplayground.kafkaClient.outgoing;

import org.eclipse.microprofile.reactive.messaging.Emitter;

public class KafkaSubmitter<T> {
    private final Emitter<T> emitter;

    public KafkaSubmitter(Emitter<T> emitter) {
        this.emitter = emitter;
    }

    public void submit(T message) {
        emitter.send(message);
    }
}