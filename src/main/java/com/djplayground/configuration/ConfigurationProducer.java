package com.djplayground.configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class ConfigurationProducer {
    @ApplicationScoped
    @Produces
    public AdapterConfigSourceFactory getAdapterConfigSourceFactory() {
        return new AdapterConfigSourceFactory();
    }
}

