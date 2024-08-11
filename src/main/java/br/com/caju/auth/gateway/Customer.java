package br.com.caju.auth.gateway;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {

    private String merchant;
    private String balanceType;



}
