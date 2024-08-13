package br.com.caju.auth.strategy;

import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.dto.TransactionStatusEnum;
import br.com.caju.auth.gateway.Customer;
import br.com.caju.auth.gateway.CustomerMok;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
public class FoodBalanceStrategy implements BalanceStrategy {

    @Override
    public TransactionStatusEnum determineBalance(TransactionDTO dto, Account account, AccountRepository accountRepository, Boolean fallback) {
        if (dto.getTotalAmount().compareTo(new BigDecimal(0)) > 0) {

            if (dto.getTotalAmount().compareTo(account.getFood()) > 0) {
                if (!fallback) {
                    log.info("[codigo {}] Saldo ALIMENTACAO insuficiente na conta {}", dto.getId(), dto.getAccount());
                    return TransactionStatusEnum.INSUFFICIENT_FUNDS;
                }
                return insufficientBalanceInCategory(dto, account, accountRepository);
            }

            account.setFood(account.getFood().subtract(dto.getTotalAmount()));
            accountRepository.save(account);
            log.info("[codigo {}] Transacao ALIMENTACAO aprovada", dto.getId());
            return TransactionStatusEnum.APPROVED;
        }
        log.info("[codigo {}] valor para ALIMENTACAO menor ou igual a 0 {}", dto.getId(), dto.getAccount());
        return TransactionStatusEnum.ERROR;
    }

    private TransactionStatusEnum insufficientBalanceInCategory(TransactionDTO dto, Account account, AccountRepository accountRepository) {
        CustomerMok customerMok = new CustomerMok();
        Customer customer = customerMok.findByName(dto.getMerchant());

        if (Objects.nonNull(customer) && customer.getBalanceType().equals("MEAL")) {

            if (dto.getTotalAmount().compareTo(account.getMeal()) > 0) {
                log.info("[FALLBACK codigo {}] Saldo MEAL insuficiente na conta {}", dto.getId(), dto.getAccount());
                return TransactionStatusEnum.INSUFFICIENT_FUNDS;
            }

            account.setMeal(account.getMeal().subtract(dto.getTotalAmount()));
            accountRepository.save(account);
            log.info("[FALLBACK codigo {}] Transacao MEAL aprovada", dto.getId());
            return TransactionStatusEnum.APPROVED;

        }

        if (dto.getTotalAmount().compareTo(account.getCash()) > 0) {
            log.info("[FALLBACK codigo {}] Saldo DINHEIRO insuficiente na conta {}", dto.getId(), dto.getAccount());
            return TransactionStatusEnum.INSUFFICIENT_FUNDS;
        }

        account.setCash(account.getCash().subtract(dto.getTotalAmount()));
        accountRepository.save(account);
        log.info("[FALLBACK codigo {}] Transacao DINHEIRO aprovada", dto.getId());
        return TransactionStatusEnum.APPROVED;
    }
}