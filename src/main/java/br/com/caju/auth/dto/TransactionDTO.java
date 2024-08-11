package br.com.caju.auth.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDTO {

    private String id;
    private String account;
    private BigDecimal totalAmount;
    private String mcc;
    private String merchant;


}
