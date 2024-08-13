package br.com.caju.auth.service;

import br.com.caju.auth.dto.ResponseDTO;
import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.dto.TransactionStatusEnum;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;
import br.com.caju.auth.strategy.BalanceStrategy;
import br.com.caju.auth.strategy.BalanceStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthService {

    AccountRepository accountRepository;

    public AuthService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
    }

    public ResponseDTO authorizer(TransactionDTO dto,Boolean fallback){
        dto.setId(UUID.randomUUID().toString());

        BalanceStrategy strategy = BalanceStrategyFactory.getStrategy(dto.getMcc());

        Optional<Account> account = accountRepository.findById(dto.getAccount());
        if (account.isEmpty()) {
            log.info("Conta não existe");
            return new ResponseDTO(TransactionStatusEnum.ERROR);
        }

        return new ResponseDTO(strategy.determineBalance(dto, account.get(),accountRepository,fallback));
    }

    public Account findAccountById(String accountId){
        Optional<Account> account = accountRepository.findById(accountId);

        return account.isEmpty() ? null : account.get();
    }


}
