package abdhamid.atm.service;

import abdhamid.atm.model.Customer;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class TransactionServiceTest {
    private TransactionService transactionService;

    private final CustomerService customerService = Mockito.mock(CustomerService.class);

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(customerService);
    }

    /**
     * Method under test:
     */
    @Test
    void testLogin() {
        // Arrange
        // TODO: Populate arranged inputs
        Customer customer = new Customer("John", "111111", 50.0, "111111");
        when(customerService.setCurrentCustomer(customer.getAccountNumber(), customer.getPin())).thenReturn(customer);
        // Act
        Customer actualLoginResult = this.transactionService.login(customer.getAccountNumber(), customer.getPin());

        // Assert
        assertAll(
                () -> assertEquals(customer.getAccountNumber(), actualLoginResult.getAccountNumber()),
                () -> assertEquals(customer.getPin() ,actualLoginResult.getPin())
        );
    }

    /**
     * Method under test: {@link TransactionService#withdraw(Integer, Customer)}
     */
    @Test
    void testWithdraw() {
        // Arrange
        Integer withdrawAmount = 10;
        Customer customer = new Customer("John", "111111", 50.0, "111111");

        // Act
        boolean actualWithdrawResult = this.transactionService.withdraw(withdrawAmount, customer);

        // Assert
        assertEquals(40.0, customer.getBalance());
    }

    /**
     * Method under test: {@link TransactionService#transfer(Customer, Customer, Integer, Integer)}
     */
    @Test
    void testTransfer() {
        // Arrange
        Customer customer = new Customer("John", "111111", 50.0, "111111");
        Customer destCustomer = new Customer("Malek", "111111", 60.0, "111112");
        Integer transferAmount = 10;
        Integer refNumber = anyInt();

        // Act
        boolean actualTransferResult = this.transactionService.transfer(customer, destCustomer, transferAmount, refNumber);

        // Assert
        assertAll(
                () -> assertEquals(40.0, customer.getBalance()),
                () -> assertEquals(70.0, destCustomer.getBalance())
        );
    }

    /**
     * Method under test: {@link TransactionService#transactionHistory(Customer)}
     */
    @Test
    void testTransactionHistory() {
        // Arrange
        // TODO: Populate arranged inputs
        Customer customer = new Customer("John", "111111", 50.0, "112233");

        // Act
        this.transactionService.transactionHistory(customer);

        // Assert
//        assertNotEquals(0, );

    }
}

