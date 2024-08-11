package br.com.caju.auth.strategy;

import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.dto.TransactionStatusEnum;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;

public class FoodBalanceStrategy implements BalanceStrategy {

    @Override
    public TransactionStatusEnum determineBalance(TransactionDTO dto, Account account,AccountRepository accountRepository) {
        if ("5411".equals(dto.getMcc()) || "5412".equals(dto.getMcc())) {

            if (dto.getTotalAmount().compareTo(account.getFood()) > 0) {
                return TransactionStatusEnum.INSUFFICIENT_FUNDS;
            }

            account.setFood(account.getFood().subtract(dto.getTotalAmount()));
            accountRepository.save(account);

            return TransactionStatusEnum.APPROVED;
        }
        return TransactionStatusEnum.ERROR;
    }
}