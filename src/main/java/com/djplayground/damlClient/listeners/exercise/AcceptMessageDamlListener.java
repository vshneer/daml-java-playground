package com.djplayground.damlClient.listeners.exercise;

import com.daml.ledger.javaapi.data.ExercisedEvent;
import com.djplayground.damlClient.listeners.base.ExercisedEventDamlListener;
import com.djplayground.damlClient.subscription.DamlLedgerSubscriber;
import com.djplayground.messageprocessing.MessageProcessor;
import main.Message;

import java.util.List;


public class AcceptMessageDamlListener extends ExercisedEventDamlListener {

    public AcceptMessageDamlListener(List<String> subscriberPartyIds, DamlLedgerSubscriber subscriber, MessageProcessor<ExercisedEvent> messageProcessor) {
        super(subscriberPartyIds, subscriber, messageProcessor, Message.TEMPLATE_ID, List.of("AcceptMessage")); // Name of the choice should be a constant
    }
}
