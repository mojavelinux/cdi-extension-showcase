package com.acme.transaction;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.UserTransaction;

@Transactional
@Interceptor
public class TransactionInterceptor {
    @Inject
    private Instance<UserTransaction> tx;

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext ctx) throws Exception {
        return new TransactionWorker() {
            public Object doWork() throws Exception {
                return ctx.proceed();
            }

        }.workInTransaction(tx.get());
    }
}
