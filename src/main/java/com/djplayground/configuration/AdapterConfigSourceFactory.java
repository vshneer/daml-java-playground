package com.djplayground.configuration;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AdapterConfigSourceFactory implements ConfigSourceFactory {
    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext context) {
        return Collections.singletonList(new AdapterConfigFactory());
    }

    public static class AdapterConfigFactory implements ConfigSource {
        private static final Map<String, String> configuration = new HashMap<>();

        public AdapterConfigFactory() {
            configuration.put("mp.messaging.incoming.proposal-message-in.enabled", "true");
        }


        @Override
        public Set<String> getPropertyNames() {
            return configuration.keySet();
        }

        @Override
        public String getValue(final String propertyName) {
            return configuration.get(propertyName);
        }

        @Override
        public String getName() {
            return AdapterConfigFactory.class.getSimpleName();
        }
    }
}
