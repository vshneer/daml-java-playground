package com.djplayground.damlClient.commandId;

import java.util.UUID;

public class RandomGenerator implements Generator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
