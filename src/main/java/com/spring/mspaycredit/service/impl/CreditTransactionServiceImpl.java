package com.spring.mspaycredit.service.impl;

import com.spring.mspaycredit.entity.Credit;
import com.spring.mspaycredit.entity.CreditTransaction;
import com.spring.mspaycredit.entity.DebitCard;
import com.spring.mspaycredit.entity.DebitCardTransaction;
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

    WebClient webClientCredit = WebClient.create("http://localhost:8887/ms-credit-charge/creditCharge");
    
    WebClient webClientDebitCard = WebClient.create("http://localhost:8887/ms-debitcard/debitCard");
    
    WebClient webClientDebitCardTransaction = WebClient.create("http://localhost:8887/ms-debitcard-transaction/debitCardTransaction");

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
        return Flux.merge(creditTransactionRepository.findByCreditId(id),
			        		webClientDebitCardTransaction.get().uri("/findAmountCreditsPaidDebitCard/{id}", id)
			                .accept(MediaType.APPLICATION_JSON)
			                .retrieve()
			                .bodyToFlux(DebitCardTransaction.class)
			                .map(dc -> CreditTransaction.builder().transactionAmount(dc.getTransactionAmount()).build()));
    }

    @Override
    public Mono<Credit> findCredit(String id) {
        return webClientCredit.get().uri("/find/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Credit.class);
    }

	@Override
	public Mono<CreditTransaction> checkUpdateBalanceDebitCard(String cardNumber, CreditTransaction credit) {
		return webClientDebitCard.put().uri("/checkUpdateBalanceDebitCard/{cardNumber}", cardNumber)
                .accept(MediaType.APPLICATION_JSON)
                .syncBody(credit)
                .retrieve()
                .bodyToMono(CreditTransaction.class);
	}

	@Override
	public Mono<DebitCard> findDebitCard(String cardNumber) {
		return webClientDebitCard.get().uri("/findCreditCardByCardNumber/{cardNumber}", cardNumber)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DebitCard.class);
	}

	@Override
	public Flux<CreditTransaction> findAmountCreditsPaidDebitCard(String id) {
		return creditTransactionRepository.findByCreditId(id);
	}

	@Override
	public Flux<CreditTransaction> findByCreditCreditCardCustomerId(String idcustomer) {
		return creditTransactionRepository.findByCreditCreditCardCustomerId(idcustomer);
	}
}
