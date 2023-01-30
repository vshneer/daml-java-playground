package com.djplayground.damlClient.listeners.base;

import com.daml.ledger.javaapi.data.Identifier;
import com.daml.ledger.javaapi.data.TransactionTree;
import com.daml.ledger.javaapi.data.TreeEvent;
import com.djplayground.damlClient.subscription.DamlLedgerSubscriber;
import com.djplayground.messageprocessing.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;


public abstract class EventDamlListener<E extends TreeEvent> {

    private static final Logger logger = LoggerFactory.getLogger(EventDamlListener.class);

    private final List<String> subscriberPartyIds;
    private final DamlLedgerSubscriber subscriber;
    private final MessageProcessor<E> messageProcessor;
    private final Identifier templateId;
    private final Class<E> eventType;
    private Predicate<TreeEvent> predicate;

    public EventDamlListener(List<String> subscriberPartyIds, DamlLedgerSubscriber subscriber,
                             MessageProcessor<E> messageProcessor,
                             Identifier templateId,
                             Class<E> eventType,
                             Predicate<TreeEvent> predicate) {
        this.subscriberPartyIds = subscriberPartyIds;
        this.subscriber = subscriber;
        this.messageProcessor = messageProcessor;
        this.templateId = templateId;
        this.eventType = eventType;
        this.predicate = predicate;
    }

    public void subscribe() {
        for (String subscriberPartyId : subscriberPartyIds) {
            subscriber.subscribe(subscriberPartyId, templateId, new TransactionConsumer());
        }
    }

    private class TransactionConsumer implements io.reactivex.functions.Consumer<TransactionTree> {

        @Override
        public void accept(TransactionTree transaction) {
            logger.info("get transactionTree {}", transaction);
            transaction.getEventsById().values().stream()
                    .filter(e -> eventType.isAssignableFrom(e.getClass()) && e.getTemplateId().equals(templateId) && predicate.test(e))
                    .map(e -> (E) e)
                    .forEach(messageProcessor);
        }
    }
}
