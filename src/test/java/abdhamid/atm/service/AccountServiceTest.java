package abdhamid.atm.service;

import abdhamid.atm.model.Account;
import abdhamid.atm.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void findAll_shouldReturnListOfAccounts() {
        Account account1 = new Account();
        Account account2 = new Account();
        List<Account> expected = List.of(account1, account2);

        when(accountRepository.findAll()).thenReturn(expected);

        List<Account> result = accountService.findAll();

        assertEquals(expected, result);
    }

    @Test
    void save_willReturnSavedAccount() {
        Account account = new Account();
        when(accountRepository.save(account)).thenReturn(account);

        Account savedAccount = accountService.save(account);

        assertEquals(account, savedAccount);
    }

    @Test
    void getByAccountNumber_willReturnAccount() {
        Account expectedAccount = new Account("Ben Dover", "123456", 100.0d, "123456");
        when(accountRepository.findCustomerByAccountNumber(expectedAccount.getAccountNumber())).thenReturn(Optional.of(expectedAccount));

        Account actualAccount = accountService.getByAccountNumber(expectedAccount.getAccountNumber());

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void getByAccountNumber_willThrowUsernameNotFoundException_whenInvokedWithInvalidAccountNumber() {
        Account expectedAccount = new Account("Ben Dover", "123456", 100.0d, "123456");
        when(accountRepository.findCustomerByAccountNumber(expectedAccount.getAccountNumber())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> accountService.getByAccountNumber(expectedAccount.getAccountNumber()));
    }

    @Test
    void getById_willReturnAccount() {
        Integer id = 1;
        Account expectedAccount = new Account("Ben Dover", "123456", 100.0d, "123456");
        when(accountRepository.findById((long) id)).thenReturn(Optional.of(expectedAccount));

        Account actualAccount = accountService.getById(id);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void getById_returnsNullWhenNotFound() {
        Integer id = 1;
        when(accountRepository.findById((long) id)).thenReturn(Optional.empty());

        Account actualAccount = accountService.getById(id);

        assertNull(actualAccount);
    }

}