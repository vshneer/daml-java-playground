package com.djplayground.damlClientTests.subscription;

import com.daml.ledger.javaapi.data.TransactionTree;
import com.daml.ledger.javaapi.data.TreeEvent;
import com.djplayground.TestUtils;
import com.djplayground.damlClient.DamlClient;
import com.djplayground.damlClient.subscription.DamlLedgerSubscriber;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DamlLedgerSubscriberTest extends TestUtils {

    @Test
    void GIVEN_transactions_WHEN_subscribe_THEN_consume_transaction() throws Exception {
        DamlClient damlClient = Mockito.mock(DamlClient.class);
        TransactionTree transactionTree = Mockito.mock(TransactionTree.class);
        TreeEvent event = getCreatedEvent(DUMMY_IDENTIFIER);
        Map<String, TreeEvent> eventsById = Map.of(DAML_EVENT_ID, event);
        when(transactionTree.getEventsById()).thenReturn(eventsById);
        Flowable<TransactionTree> transactionStream = Flowable.just(transactionTree, transactionTree);
        when(damlClient.getTransactionTree(any(), any())).thenReturn(transactionStream);

        DamlLedgerSubscriber subscriber = new DamlLedgerSubscriber(damlClient);
        Consumer<TransactionTree> consumer = spy((new Consumer<TransactionTree>() {
            @Override
            public void accept(TransactionTree t) {
                // Do nothing.
                // We need to keep the `new Consumer..` form because using lambda
                // makes the test fail (spy does not like it)
            }
        }));
        subscriber.subscribe(ADMIN_PARTY_ID, DUMMY_IDENTIFIER, consumer);

        verify(consumer, times(2)).accept(transactionTree);
    }
}
