package br.com.caju.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDTO {

    @JsonIgnore
    private String id;
    private String account;
    private BigDecimal totalAmount;
    private String mcc;
    private String merchant;


}
