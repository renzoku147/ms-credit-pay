package com.spring.mspaycredit.service;

import com.spring.mspaycredit.entity.Credit;
import com.spring.mspaycredit.entity.CreditTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditTransactionService {
    Mono<CreditTransaction> create(CreditTransaction t);

    Flux<CreditTransaction> findAll();

    Mono<CreditTransaction> findById(String id);

    Mono<CreditTransaction> update(CreditTransaction t);

    Mono<Boolean> delete(String t);

    Flux<CreditTransaction> findCreditsPaid(String id);

    Mono<Credit> findCredit(String id);
}
