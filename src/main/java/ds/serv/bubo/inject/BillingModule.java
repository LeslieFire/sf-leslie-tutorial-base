package ds.serv.bubo.inject;

import com.google.inject.AbstractModule;

/**
 * Author: Leslie
 * Date: 3/9/2016.
 */
public class BillingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TransactionLog.class).to(DatabaseTransactionLog.class);
    }
}
