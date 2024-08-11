package br.com.caju.auth.strategy;

import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.dto.TransactionStatusEnum;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;

public class MealBalanceStrategy implements BalanceStrategy {
    @Override
    public TransactionStatusEnum determineBalance(TransactionDTO dto, Account account, AccountRepository accountRepository) {
        if ("5811".equals(dto.getMcc()) || "5812".equals(dto.getMcc())) {
            return TransactionStatusEnum.APPROVED;
        }
        return TransactionStatusEnum.ERROR;
    }
}