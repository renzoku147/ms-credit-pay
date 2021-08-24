package com.spring.mspaycredit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credit {
    private String id;

    private CreditCard creditCard;

    private Double amount;
    
    private Integer numberQuota;

    private LocalDateTime date;

}
