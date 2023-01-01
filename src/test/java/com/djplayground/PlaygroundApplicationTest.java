package com.djplayground;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;


@QuarkusTest
public class PlaygroundApplicationTest {

    @Inject
    PlaygroundApplication application;

    @Test
    void startup() {}

}
