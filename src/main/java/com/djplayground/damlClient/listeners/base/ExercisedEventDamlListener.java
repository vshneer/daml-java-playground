package com.djplayground.damlClient.listeners.base;

import com.daml.ledger.javaapi.data.ExercisedEvent;
import com.daml.ledger.javaapi.data.Identifier;
import com.daml.ledger.javaapi.data.TreeEvent;
import com.djplayground.damlClient.subscription.DamlLedgerSubscriber;
import com.djplayground.messageprocessing.MessageProcessor;

import java.util.List;
import java.util.function.Predicate;

public abstract class ExercisedEventDamlListener extends EventDamlListener<ExercisedEvent> {
    public ExercisedEventDamlListener(List<String> subscriberPartyIds, DamlLedgerSubscriber subscriber, MessageProcessor<ExercisedEvent> messageProcessor,
                                      Identifier templateId, List<String> choiceNames) {
        super(subscriberPartyIds, subscriber, messageProcessor, templateId, ExercisedEvent.class, hasChoiceName(choiceNames));
    }

    private static Predicate<TreeEvent> hasChoiceName(List<String> choiceNames) {
        return treeEvent -> {
            var protoTreeEvent = treeEvent.toProtoTreeEvent();
            return protoTreeEvent.hasExercised() &&
                choiceNames.stream().anyMatch(
                    choiceName -> protoTreeEvent.getExercised().getChoice().equals(choiceName));
        };
    }
}
