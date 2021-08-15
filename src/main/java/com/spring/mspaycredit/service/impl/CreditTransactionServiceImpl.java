package com.spring.mspaycredit.service.impl;

import com.spring.mspaycredit.entity.Credit;
import com.spring.mspaycredit.entity.CreditTransaction;
import com.spring.mspaycredit.repository.CreditTransactionRepository;
import com.spring.mspaycredit.service.CreditTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditTransactionServiceImpl implements CreditTransactionService {

    WebClient webClient = WebClient.create("http://localhost:8887/ms-credit-charge/creditcharge/creditCharge");

    @Autowired
    private CreditTransactionRepository creditTransactionRepository;

    @Override
    public Mono<CreditTransaction> create(CreditTransaction t) {
        return creditTransactionRepository.save(t);
    }

    @Override
    public Flux<CreditTransaction> findAll() {
        return creditTransactionRepository.findAll();
    }

    @Override
    public Mono<CreditTransaction> findById(String id) {
        return creditTransactionRepository.findById(id);
    }

    @Override
    public Mono<CreditTransaction> update(CreditTransaction t) {
        return creditTransactionRepository.save(t);
    }

    @Override
    public Mono<Boolean> delete(String t) {
        return creditTransactionRepository.findById(t)
                .flatMap(tar -> creditTransactionRepository.delete(tar).then(Mono.just(Boolean.TRUE)))
                .defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Flux<CreditTransaction> findCreditsPaid(String id) {
        return creditTransactionRepository.findByCreditId(id);
    }

    @Override
    public Mono<Credit> findCredit(String id) {
        return webClient.get().uri("/find/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Credit.class);
    }
}
