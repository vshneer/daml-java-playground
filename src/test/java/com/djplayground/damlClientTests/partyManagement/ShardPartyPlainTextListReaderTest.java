package com.djplayground.damlClientTests.partyManagement;

import com.djplayground.damlClient.partyManagement.PartyPlainTextListReader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShardPartyPlainTextListReaderTest {

  @Test
  void initializeFromNonExistingPathFails() {
    Path path = Path.of("? % dummy % ?");
    assertThrows(
        FileNotFoundException.class,
        () -> PartyPlainTextListReader.initializeFromPath(path));
  }

  @Test
  void emptyFileResultsInNoParties() {
    var input = new StringReader("");
    var sut = new PartyPlainTextListReader(input);

    assertEquals(0, sut.getParties().size());
  }

  @Test
  void singleLineFileResultsInOneParty() {
    var party = "party";
    var input = new StringReader(party);
    var sut = new PartyPlainTextListReader(input);

    assertThat(sut.getParties(), contains(party));
  }

  @Test
  void multiLineFileResultsInParties() {
    var party1 = "partyA";
    var party2 = "partyB";
    var party3 = "partyC";
    var multiLine = String.join("\n", List.of(party1, party2, party3));
    var input = new StringReader(multiLine);
    var sut = new PartyPlainTextListReader(input);

    assertThat(sut.getParties(), contains(party1, party2, party3));
  }

  @Test
  void emptyLinesAreFiltered() {
    var party1 = "partyA";
    var party2 = "partyB";
    var multiLine = String.join("\n", List.of("", party1, "", "", "", party2));
    var input = new StringReader(multiLine);
    var sut = new PartyPlainTextListReader(input);

    assertThat(sut.getParties(), contains(party1, party2));
  }
}
