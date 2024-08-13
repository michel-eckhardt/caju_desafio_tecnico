package br.com.caju.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransactionDTO {

    @JsonIgnore
    private String id;

    @NotNull
    private String account;
    @NotNull
    private BigDecimal totalAmount;
    @NotNull
    private String mcc;
    @NotNull
    private String merchant;


}
