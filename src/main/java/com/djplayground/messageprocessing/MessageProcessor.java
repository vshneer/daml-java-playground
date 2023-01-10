package com.djplayground.messageprocessing;

import java.util.function.Consumer;

public abstract class MessageProcessor<M> implements Consumer<M> {
    @Override
    public void accept(M input) {
        publish(input);
    }

    protected void publish(M input) {
        //default to no kafka submission
    }

}
