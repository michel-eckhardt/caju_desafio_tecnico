package br.com.caju.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import br.com.caju.auth.dto.ResponseDTO;
import br.com.caju.auth.dto.TransactionDTO;
import br.com.caju.auth.dto.TransactionStatusEnum;
import br.com.caju.auth.model.Account;
import br.com.caju.auth.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AuthService authService;

    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        transactionDTO = new TransactionDTO();
        transactionDTO.setAccount("123");
        transactionDTO.setMerchant("PADARIA DO ZE               SAO PAULO BR");
        transactionDTO.setTotalAmount(new BigDecimal(50));
    }

    @Test
    void testMealAuthorizerApproved(){
        transactionDTO.setMcc("5811");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(0),new BigDecimal(1000),new BigDecimal(0))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.APPROVED);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,true);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testFoodAuthorizerApproved(){
        transactionDTO.setMcc("5411");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(1000),new BigDecimal(0),new BigDecimal(0))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.APPROVED);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,true);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testCashAuthorizerApproved(){
        transactionDTO.setMcc("9999");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(0),new BigDecimal(0),new BigDecimal(1000))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.APPROVED);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,true);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testMealAuthorizerApprovedWithFallback(){
        transactionDTO.setMcc("5811");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(1000),new BigDecimal(1),new BigDecimal(1000))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.APPROVED);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,true);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testFoodAuthorizerApprovedWithFallback(){
        transactionDTO.setMcc("5411");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(1),new BigDecimal(1000),new BigDecimal(1000))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.APPROVED);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,true);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testMealAuthorizerInsufficientFounds(){
        transactionDTO.setMcc("5811");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(1000),new BigDecimal(1),new BigDecimal(1000))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.INSUFFICIENT_FUNDS);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,false);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testFoodAuthorizerInsufficientFounds(){
        transactionDTO.setMcc("5411");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(1),new BigDecimal(1000),new BigDecimal(1000))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.INSUFFICIENT_FUNDS);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,false);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testCashAuthorizerInsufficientFounds(){
        transactionDTO.setMcc("999");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(1000),new BigDecimal(1000),new BigDecimal(1))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.INSUFFICIENT_FUNDS);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,false);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testMealAuthorizerInsufficientFoundsWithFallback(){
        transactionDTO.setMcc("5811");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(1000),new BigDecimal(1),new BigDecimal(1))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.INSUFFICIENT_FUNDS);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,true);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testFoodAuthorizerInsufficientFoundsWithFallback(){
        transactionDTO.setMcc("5411");
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.of(
                new Account("123",new BigDecimal(1),new BigDecimal(10),new BigDecimal(1))));
        ResponseDTO expectedResponseDTO = new ResponseDTO(TransactionStatusEnum.INSUFFICIENT_FUNDS);

        ResponseDTO responseDTO = authService.authorizer(transactionDTO,true);

        assertEquals(expectedResponseDTO.getCode(), responseDTO.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

    @Test
    void testAuthorizerAccountDoesNotExist() {
        when(accountRepository.findById(transactionDTO.getAccount())).thenReturn(Optional.empty());

        ResponseDTO response = authService.authorizer(transactionDTO, true);

        assertEquals(TransactionStatusEnum.ERROR, response.getCode());
        verify(accountRepository, times(1)).findById(transactionDTO.getAccount());
    }

}
