package com.djplayground.configuration;

import io.smallrye.config.ConfigMapping;

import java.nio.file.Path;

@ConfigMapping(prefix = "playground")
public interface PlaygroundConfiguration {
  Path proposerConfig();
  Path counterpartyConfig();
}
