package com.djplayground.damlClient.partyManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/*
Example file content: parties separated by newline.

partyid1
partyid2
partyid3
...
 */
public class PartyPlainTextListReader implements PartyReader {
  private final BufferedReader reader;
  private List<String> shardParties;

  public static PartyPlainTextListReader initializeFromPath(Path path) throws FileNotFoundException {
    FileReader fileReader = new FileReader(path.toFile());
    return new PartyPlainTextListReader(fileReader);
  }

  public PartyPlainTextListReader(Reader input) {
    this.reader = new BufferedReader(input);
    this.shardParties = null;
  }

  @Override
  public List<String> getParties() {
    if (shardParties == null) {
      shardParties = reader.lines().filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }
    return shardParties;
  }
}
