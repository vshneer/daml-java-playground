package com.djplayground.messageprocessing.daml;

import com.daml.ledger.javaapi.data.ExercisedEvent;
import com.djplayground.messageprocessing.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class DamlAcceptMessageChoiceExerciseProcessor extends MessageProcessor<ExercisedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DamlAcceptMessageChoiceExerciseProcessor.class);


    public DamlAcceptMessageChoiceExerciseProcessor() {
        logger.info("Created DamlAcceptMessageChoiceExerciseProcessor");
    }

    @Override
    public void publish(ExercisedEvent arg) {
        logger.info("DamlAcceptMessageChoiceExerciseProcessor is about to publish message {}", arg);
    }
}
/*
ExercisedEvent{
    witnessParties=[counterparty::12207c5abc01c9bea1172ff60f5f038a4244c6908811f6bf102472008a4b0d275c50],
    eventId='#1220aaa5daf7e6d7a1c838b59489de92d5b296517a0a52938e6678f7d1c95ee83b64:0',
    templateId=Identifier{packageId='23ac3523a1df82162121b226d9decfb2a459991aa182df1e82d937b75695b954', moduleName='Main', entityName='Proposal'},
    interfaceId=Optional.empty,
    contractId='00241e8e5773190541a8a9aca43f0b910d1427461ea92da19ac6efb44fdc5f9e63ca011220321c4a2b156c38c8ec2c7fe1b66357934907cb7b3d624d56ea28c0d08ef434ed',
    choice='Accept',
    choiceArgument=DamlRecord{recordId=Optional[Identifier{packageId='23ac3523a1df82162121b226d9decfb2a459991aa182df1e82d937b75695b954', moduleName='Main', entityName='Accept'}], fields=[]},
    actingParties=[counterparty::12207c5abc01c9bea1172ff60f5f038a4244c6908811f6bf102472008a4b0d275c50], '
    consuming=true,
    childEventIds=[#1220aaa5daf7e6d7a1c838b59489de92d5b296517a0a52938e6678f7d1c95ee83b64:1],
    exerciseResult=ContractId{value='000ac0c49a74dc99baa3e33468fccb7d50a0bf361d218462f193f969a3dc652c4dca011220bff401987f46c61e3c9400351309562bab13fa21b4ad333974faeafc39c167b7'}}
*/