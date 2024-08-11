package br.com.caju.auth.strategy;

import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.dto.TransactionStatusEnum;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;

public class DefaultBalanceStrategy implements BalanceStrategy {
    @Override
    public TransactionStatusEnum determineBalance(TransactionDTO dto, Account account, AccountRepository accountRepository) {
        return TransactionStatusEnum.APPROVED;
    }
}