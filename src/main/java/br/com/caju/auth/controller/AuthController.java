package br.com.caju.auth.controller;

import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;
import br.com.caju.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/v1")
public class AuthController {

    AuthService authService;

    @Autowired
    AccountRepository accountRepository;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("health")
    public String health(){
        return "status: online";
    }

    @PostMapping("authorizer")
    public ResponseEntity<?> authorizer(@RequestBody TransactionDTO dto, @RequestHeader Boolean fallback){



        return ResponseEntity.ok().body(authService.authorizer(dto,fallback));
    }


    @GetMapping("/account/{accountId}")
    public Account findById(@PathVariable String accountId){
        return accountRepository.findById(accountId).get();
    }

}
