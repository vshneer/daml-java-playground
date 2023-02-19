package com.djplayground.kafkaClient;


import com.djplayground.kafkaClient.message.KafkaMessageEventId;
import com.djplayground.kafkaClient.outgoing.KafkaSubmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


public class KafkaSubmitterProducer {
    @ApplicationScoped
    @Named("KafkaEventIdSubmitter")
    KafkaSubmitter<KafkaMessageEventId> getKafkaProposalSubmitter(
            @Channel("eventid-message-out") Emitter<KafkaMessageEventId> proposalEmitter) {
        return new KafkaSubmitter<>(proposalEmitter);
    }

}