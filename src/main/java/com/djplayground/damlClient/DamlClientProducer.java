package com.djplayground.damlClient;

import com.djplayground.configuration.DamlLedgerConfiguration;
import com.djplayground.damlClient.implementation.PlaygroundDamlClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class DamlClientProducer {
    Logger logger = LoggerFactory.getLogger(DamlClientProducer.class);

    @ApplicationScoped
    @Produces
    public DamlClient getDefaultDamlClient(DamlLedgerConfiguration damlLedgerConfiguration) {
        String host = damlLedgerConfiguration.host();
        int port = damlLedgerConfiguration.port();
        logger.info("Created DamlClient with host {} and port {}", host, port);
        return new PlaygroundDamlClient(host, port);
    }

}
