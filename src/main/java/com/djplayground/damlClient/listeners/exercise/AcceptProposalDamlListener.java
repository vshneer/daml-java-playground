package com.djplayground.damlClient.listeners.exercise;

import com.daml.ledger.javaapi.data.ExercisedEvent;
import com.daml.ledger.javaapi.data.Identifier;
import com.djplayground.damlClient.listeners.base.ExercisedEventDamlListener;
import com.djplayground.damlClient.subscription.DamlLedgerSubscriber;
import com.djplayground.messageprocessing.MessageProcessor;
import main.Proposal;

import java.util.List;

/*
 *  HOMEWORK 5
 *
 *  Add and produce your exercise Daml listener
 *
 *  1. Create your DamlListener link the one you see below
 *  2. Your class should extend ExercisedEventDamlListener
 *  3. You would need to pass you Template Id and Choice name. The same way you see below
 *  4. The last step would be adding creating a producer method for your class under DamlListenerProducer class
 *  5. To produce listener you would need to pass your processor. You can use the same processor that was used for AcceptProposer listener - DamlAcceptProposalChoiceExerciseProcessor
 *          or you can make a little step forward to the next session and implement your own processor that you will continue to work on, on the next session.
 *          Make it simple as DamlAcceptProposalChoiceExerciseProcessor. Just log message. You will implement translation on the next session
 *
 *  You can see that the practice part of this session is not that big. That's because the most of the code was written on listener setup.
 *  To understand how it is working I suggest you to go through all the classes that you can find under "listeners" and "subscription" folders
 *  Follow the connections between classes. HINT: Listener producer method connects Subscription and Listener together
 *
 *
 *  Next time we will talk about Daml event translation and Kafka submission.
 *
 * */


public class AcceptProposalDamlListener extends ExercisedEventDamlListener {

    public AcceptProposalDamlListener(List<String> subscriberPartyIds, DamlLedgerSubscriber subscriber, MessageProcessor<ExercisedEvent> messageProcessor) {
        super(subscriberPartyIds, subscriber, messageProcessor, Proposal.TEMPLATE_ID, List.of("Accept")); // Name of the choice should be a constant
    }
}
