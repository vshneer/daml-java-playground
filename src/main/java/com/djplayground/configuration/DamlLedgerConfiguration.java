package com.djplayground.configuration;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "daml.ledger")
public interface DamlLedgerConfiguration {
  String host();
  int port();
}
