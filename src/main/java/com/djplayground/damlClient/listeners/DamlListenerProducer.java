package com.djplayground.damlClient.listeners;

import com.djplayground.damlClient.listeners.exercise.AcceptProposalDamlListener;
import com.djplayground.damlClient.partyManagement.PartyReader;
import com.djplayground.damlClient.subscription.DamlLedgerSubscriber;
import com.djplayground.messageprocessing.daml.DamlAcceptProposalChoiceExerciseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

public class DamlListenerProducer {

    Logger logger = LoggerFactory.getLogger(DamlListenerProducer.class);

    @Singleton
    @Produces
    public AcceptProposalDamlListener getAcceptProposalDamlListener(DamlLedgerSubscriber subscriber,
                                                                    DamlAcceptProposalChoiceExerciseProcessor messageProcessor,
                                                                    PartyReader partyReader) {
            logger.info("Created AcceptProposalDamlListener");
            AcceptProposalDamlListener acceptProposalDamlListener = new AcceptProposalDamlListener(
                    partyReader.getParties(),
                    subscriber,
                    messageProcessor);
            acceptProposalDamlListener.subscribe();
            return acceptProposalDamlListener;
        }
    }


