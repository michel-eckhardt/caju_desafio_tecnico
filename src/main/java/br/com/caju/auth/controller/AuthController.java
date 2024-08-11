package br.com.caju.auth.controller;

import br.com.caju.auth.dto.ResponseDTO;
import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;
import br.com.caju.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Autorização de transaçãoes", notes = "Autoriza ou rejeita transações",response = ResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{ \"code\": \"00\" } se a transação é aprovada\n" +
                    "{ \"code\": \"51\" } se a transação é rejeitada, porque não tem saldo suficiente\n" +
                    "{ \"code\": \"07\" } se acontecer qualquer outro problema que impeça a transação de ser processada",
            response = ResponseDTO.class)
    })
    @PostMapping("authorizer")
    public ResponseEntity<?> authorizer(@RequestBody TransactionDTO dto,
                                        @ApiParam(value = "Ativa/Desativa a opção de Fallback", required = true)@RequestHeader Boolean fallback){
        return ResponseEntity.ok().body(authService.authorizer(dto,fallback));
    }


    @ApiOperation(value = "Consultar Account", notes = "Busca Accounts por ids")
    @GetMapping("/account/{accountId}")
    public Account findById(@ApiParam(value = "ID da conta a ser buscada", required = true)@PathVariable String accountId){
        return accountRepository.findById(accountId).get();
    }

}
