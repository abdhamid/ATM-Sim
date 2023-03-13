package abdhamid.atm.service;

import abdhamid.atm.model.Customer;

import java.util.List;
import java.util.Optional;

import abdhamid.atm.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;


    @Test
    void testFindAll() {
        // Arrange and Act
        List<Customer> res = List.of(new Customer("Bob", "112233", 100. , "112233"));
        Mockito.when(accountService.findAll()).thenReturn(res);

        List<Customer> actualFindAllResult = this.accountService.findAll();

        // Assert
        assertEquals(res, actualFindAllResult);
    }


    @Test
    void testSave() {
        // Arrange
        Customer customer = new Customer("Bob", "112233", 100. , "112233");

        // Act
        Mockito.when(accountService.save(customer)).thenReturn(customer);
        Customer actualSaveResult = this.accountService.save(customer);

        // Assert
        assertEquals(customer, actualSaveResult);
    }

    @Test
    void testFindByAccountNumber() {
        // Arrange
        Optional<Customer> customer = Optional.of(new Customer("Bob", "112233", 100., "112233"));

        Mockito.when(accountService.findByAccountNumber("112233")).thenReturn(customer);

        // Act
        Optional<Customer> actualFindByAccountNumberResult = this.accountService.findByAccountNumber("112233");

        // Assert
        assertEquals(customer, actualFindByAccountNumberResult);
    }
}

