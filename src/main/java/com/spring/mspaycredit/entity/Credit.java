package com.spring.mspaycredit.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Credit {

    private String id;

    private CreditCard creditCard;

    private Double amount;

    private LocalDateTime date;

}
