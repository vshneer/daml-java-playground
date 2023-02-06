package com.djplayground.damlClient.subscription;

import com.daml.ledger.javaapi.data.Identifier;
import com.daml.ledger.javaapi.data.TransactionTree;
import io.reactivex.functions.Consumer;

public interface LedgerSubscriber<R> {
     R subscribe (String subscribeParty, Identifier templateId, Consumer<TransactionTree> consumer);
}
