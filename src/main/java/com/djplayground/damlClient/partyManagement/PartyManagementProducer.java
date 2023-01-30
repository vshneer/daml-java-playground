package com.djplayground.damlClient.partyManagement;

import com.djplayground.configuration.PlaygroundConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileNotFoundException;


public class PartyManagementProducer {

    Logger logger = LoggerFactory.getLogger(PartyManagementProducer.class);

    @ApplicationScoped
    public PartyPlainTextListReader getAssemblerShardPartyReader(PlaygroundConfiguration adapterConfiguration) throws FileNotFoundException {
            logger.info("Created PartyPlainTextListReader");
            return PartyPlainTextListReader.initializeFromPath(adapterConfiguration.partiesConfig());
    }
}


