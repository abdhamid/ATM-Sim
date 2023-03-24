package abdhamid.atm.service;

import abdhamid.atm.dto.TransferDto;
import abdhamid.atm.model.Account;
import abdhamid.atm.model.Transaction;
import abdhamid.atm.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    AccountService accountService;

    private Account sender;
    private Account receiver;

    @BeforeEach
    void setup() {
        sender = new Account("Ben", "123456", 1000.0, "123456");
        receiver = new Account("Dover", "234567", 500.0, "234567");
    }
    @Test
    void withdraw_willReturnTransaction_whenInvokedWithValidAmount() throws Exception {
        int withdrawAmount = 500;
        lenient().when(accountService.getByAccountNumber(sender.getAccountNumber())).thenReturn(sender);


        Transaction result = transactionService.withdraw(sender, withdrawAmount);

        assertNotNull(result);
        assertEquals("DEBIT", result.getTransactionType());
        assertEquals(sender.getAccountNumber(), result.getTransactionCreator());
        assertEquals((double) withdrawAmount, result.getAmount());
    }

    @Test
    void withdraw_willThrowInputMismatchException_whenInvokedWithInvalidAmount() {
        int withdrawAmount = 1500;

        assertThrows(InputMismatchException.class, () -> transactionService.withdraw(sender, withdrawAmount));
    }

    @Test
    void withdraw_withInsufficientBalance_shouldThrowInputMismatchException() throws Exception {
        int withdrawAmount = 500;
        sender.setBalance(400.);
        lenient().when(accountService.getByAccountNumber(sender.getAccountNumber())).thenReturn(sender);

        assertThrows(InputMismatchException.class, () -> transactionService.withdraw(sender, withdrawAmount));
    }

    @Test
    void transfer_willReturnTransaction_whenInvokedWithValidTransferDto() {
        TransferDto transferDto = new TransferDto(receiver.getAccountNumber(), 500);

        when(accountService.getByAccountNumber(receiver.getAccountNumber())).thenReturn(receiver);
        when(accountService.save(receiver)).thenReturn(receiver);
        when(accountService.save(sender)).thenReturn(sender);

        Transaction result = transactionService.transfer(sender, transferDto);

        assertNotNull(result);
        assertEquals("DEBIT", result.getTransactionType());
        assertEquals(sender.getAccountNumber(), result.getTransactionCreator());
        assertEquals((double) transferDto.getAmount(), result.getAmount());
        assertEquals(500.0, sender.getBalance());
        assertEquals(1000.0, receiver.getBalance());
        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(accountService, times(2)).save(any(Account.class));
    }

    @Test
    void transactionHistory_validAccountNumber_returnsPage() {
        String accNumber = "123456";
        Pageable page = Pageable.unpaged();
        when(transactionRepository.findByAccNumberDesc(anyString(), any(Pageable.class))).thenReturn(Page.empty());

        Page<Transaction> result = transactionService.transactionHistory(accNumber, page);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(transactionRepository, times(1)).findByAccNumberDesc(anyString(), any(Pageable.class));
    }

    @Test
    void validateTransactionAmount_willReturnCorrectOutput() {
        // Valid amount
        Assertions.assertEquals("50", transactionService.validateTransactionAmount("50", 10, 1000));

        // Invalid amount (not a multiple of 10)
        Assertions.assertEquals("Amount must be in a multiple of 10", transactionService.validateTransactionAmount("53", 10, 1000));

        // Invalid amount (too large)
        Assertions.assertEquals("Maximum amount to transfer is $1000", transactionService.validateTransactionAmount("1500", 10, 1000));

        // Invalid amount (too small)
        Assertions.assertEquals("Minimum amount to transfer is $1", transactionService.validateTransactionAmount("0", 10, 1000));

        // Invalid amount (not a number)
        Assertions.assertEquals("Invalid amount", transactionService.validateTransactionAmount("abc", 10, 1000));
    }
}