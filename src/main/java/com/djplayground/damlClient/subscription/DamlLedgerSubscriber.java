package com.djplayground.damlClient.subscription;

import com.daml.ledger.javaapi.data.Identifier;
import com.daml.ledger.javaapi.data.LedgerOffset;
import com.daml.ledger.javaapi.data.TransactionTree;
import com.djplayground.damlClient.DamlClient;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DamlLedgerSubscriber implements LedgerSubscriber<Disposable> {

    private final DamlClient ledger;

    public DamlLedgerSubscriber(DamlClient ledger) {
        this.ledger = ledger;
    }

    @Override
    public Disposable subscribe(String subscribeParty, Identifier templateId, Consumer<TransactionTree> consumer) {
        var transactions = ledger.getTransactionTree(
                subscribeParty, LedgerOffset.LedgerEnd.getInstance());

        // all transactions visible by the party will be passed to consumer(processor for filtering)
        return transactions.forEach(consumer);
    }
}
