package com.djplayground;

import com.djplayground.configuration.DamlLedgerConfiguration;
import com.djplayground.configuration.PlaygroundConfiguration;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@QuarkusMain
public class PlaygroundApplication {

    @Inject
    PlaygroundConfiguration adapterConfiguration;
    @Inject
    DamlLedgerConfiguration damlLedgerConfiguration;

    public static void main(String... args) {
        System.out.println("Playground application started..");
        Quarkus.run(AdapterApplicationStarter.class, args);
    }

    @ApplicationScoped
    public static class AdapterApplicationStarter implements QuarkusApplication {

        @Override
        public int run(String... args) {
            System.out.println("Let's go!");
            Quarkus.waitForExit();
            return 0;
        }
    }
}
