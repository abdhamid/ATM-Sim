package abdhamid.atm.service;

import abdhamid.atm.model.Account;

import java.util.List;
import java.util.Optional;

import abdhamid.atm.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountService.class})
@ExtendWith(SpringExtension.class)
class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;


    @Test
    void testFindAll() {
        // Arrange and Act
        List<Account> res = List.of(new Account("Bob", "112233", 100. , "112233"));
        Mockito.when(accountService.findAll()).thenReturn(res);

        List<Account> actualFindAllResult = this.accountService.findAll();

        // Assert
        assertEquals(res, actualFindAllResult);
    }


    @Test
    void testSave() {
        // Arrange
        Account account = new Account("Bob", "112233", 100. , "112233");

        // Act
        Mockito.when(accountService.save(account)).thenReturn(account);
        Account actualSaveResult = this.accountService.save(account);

        // Assert
        assertEquals(account, actualSaveResult);
    }

    @Test
    void testFindByAccountNumber() {
        // Arrange
        Optional<Account> customer = Optional.of(new Account("Bob", "112233", 100., "112233"));

        Mockito.when(accountService.findByAccountNumber("112233")).thenReturn(customer);

        // Act
        Optional<Account> actualFindByAccountNumberResult = this.accountService.findByAccountNumber("112233");

        // Assert
        assertEquals(customer, actualFindByAccountNumberResult);
    }
}

