package br.com.caju.auth.strategy;

import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.dto.TransactionStatusEnum;
import br.com.caju.auth.gateway.Customer;
import br.com.caju.auth.gateway.CustomerMok;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MealBalanceStrategy implements BalanceStrategy {
    @Override
    public TransactionStatusEnum determineBalance(TransactionDTO dto, Account account, AccountRepository accountRepository,Boolean fallback) {
        if ("5811".equals(dto.getMcc()) || "5812".equals(dto.getMcc())) {

            if (dto.getTotalAmount().compareTo(account.getMeal()) > 0) {
                if (fallback){
                    return insufficientBalanceInCategory(dto,account,accountRepository);
                }
                log.info("[codigo {}] Saldo REFEICAO insuficiente na conta {}",dto.getId(),dto.getAccount());
                return TransactionStatusEnum.INSUFFICIENT_FUNDS;
            }

            account.setMeal(account.getMeal().subtract(dto.getTotalAmount()));
            accountRepository.save(account);
            log.info("[codigo {}] Transacao ALIMENTACAO aprovada",dto.getId());
            return TransactionStatusEnum.APPROVED;
        }
        return TransactionStatusEnum.ERROR;
    }

    private TransactionStatusEnum insufficientBalanceInCategory(TransactionDTO dto, Account account,AccountRepository accountRepository){

        CustomerMok customerMok = new CustomerMok();
        Customer customer = customerMok.findByName(dto.getMerchant());

        if (customer != null){
            if(customer.getBalanceType().equals("FOOD")){

                if (dto.getTotalAmount().compareTo(account.getFood()) > 0) {
                    log.info("[FALLBACK codigo {}] Saldo FOOD insuficiente na conta {}",dto.getId(), dto.getAccount());
                    return TransactionStatusEnum.INSUFFICIENT_FUNDS;
                }

                account.setFood(account.getFood().subtract(dto.getTotalAmount()));
                accountRepository.save(account);
                log.info("[FALLBACK codigo {}] Transacao FOOD aprovada",dto.getId());
                return TransactionStatusEnum.APPROVED;

            }
        }

        if (dto.getTotalAmount().compareTo(account.getCash()) > 0) {
            log.info("[FALLBACK codigo {}] Saldo DINHEIRO insuficiente na conta {}",dto.getId(), dto.getAccount());
            return TransactionStatusEnum.INSUFFICIENT_FUNDS;
        }

        account.setCash(account.getCash().subtract(dto.getTotalAmount()));
        accountRepository.save(account);
        log.info("[FALLBACK codigo {}] Transacao DINHEIRO aprovada",dto.getId());
        return TransactionStatusEnum.APPROVED;
    }

}