package com.djplayground.damlClient.listeners.exercise;

import com.daml.ledger.javaapi.data.ExercisedEvent;
import com.daml.ledger.javaapi.data.Identifier;
import com.djplayground.damlClient.listeners.base.ExercisedEventDamlListener;
import com.djplayground.damlClient.subscription.DamlLedgerSubscriber;
import com.djplayground.messageprocessing.MessageProcessor;
import main.Proposal;

import java.util.List;

public class AcceptProposalDamlListener extends ExercisedEventDamlListener {

    public AcceptProposalDamlListener(List<String> subscriberPartyIds, DamlLedgerSubscriber subscriber, MessageProcessor<ExercisedEvent> messageProcessor) {
        super(subscriberPartyIds, subscriber, messageProcessor, Proposal.TEMPLATE_ID, List.of("Accept")); // Name of the choice should be a constant
    }
}
