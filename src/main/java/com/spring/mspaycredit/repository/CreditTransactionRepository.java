package com.spring.mspaycredit.repository;

import com.spring.mspaycredit.entity.CreditTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditTransactionRepository extends ReactiveMongoRepository<CreditTransaction, String> {

    Flux<CreditTransaction> findByCreditId(String id);
}

