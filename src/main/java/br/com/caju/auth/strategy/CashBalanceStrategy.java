package br.com.caju.auth.strategy;

import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.dto.TransactionStatusEnum;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class CashBalanceStrategy implements BalanceStrategy {
    @Override
    public TransactionStatusEnum determineBalance(TransactionDTO dto, Account account, AccountRepository accountRepository,Boolean fallback) {

        if (dto.getTotalAmount().compareTo(new BigDecimal(0)) < 1) {
            return  TransactionStatusEnum.ERROR;
        }

        if (dto.getTotalAmount().compareTo(account.getCash()) > 0) {
            log.info("Saldo DINHEIRO insuficiente na conta {}",dto.getAccount());
            return TransactionStatusEnum.INSUFFICIENT_FUNDS;
        }

        account.setCash(account.getCash().subtract(dto.getTotalAmount()));
        accountRepository.save(account);
        log.info("Transacao DINHEIRO codigo {} aprovada",dto.getId());
        return TransactionStatusEnum.APPROVED;

    }
}