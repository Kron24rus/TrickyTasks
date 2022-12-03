package ru.mikheev.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionalService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void transactionOne();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void transactionTwo();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void transactionBoth();
}
