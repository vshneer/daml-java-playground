package com.djplayground.messageprocessing;

import java.util.function.Consumer;

public abstract class MessageProcessor<M> implements Consumer<M> {
  @Override
    public void accept(M input) {

        updateCache(input);
        publish(input);
    }

    protected void updateCache(M input) {
        //default to no cache update
    }

    protected void publish(M input) {
        //default to no kafka submission
    }
     // todo refactor logging

}
