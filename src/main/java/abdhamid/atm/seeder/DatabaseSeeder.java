package abdhamid.atm.seeder;

import abdhamid.atm.dao.CustomerDao;
import abdhamid.atm.model.Account;
import abdhamid.atm.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static abdhamid.atm.config.FileConfiguration.CUSTOMER_PATH;

@Configuration
public class DatabaseSeeder {
    private final AccountService accountService;
    private final CustomerDao customerDao;
    private final AccountSeeder accountSeeder;
    private final TransactionSeeder transactionSeeder;

    public DatabaseSeeder(AccountService accountService, AccountSeeder accountSeeder, TransactionSeeder transactionSeeder) {
        this.accountService = accountService;
        this.accountSeeder = accountSeeder;
        this.transactionSeeder = transactionSeeder;
        this.customerDao = new CustomerDao();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> seeder();
    }

    private void customerSeeder() {
        List<Account> accounts = customerDao.readCustomerCSV(CUSTOMER_PATH);
        for (Account account : accounts) {
            accountService.save(account);
        }
    }

    private void seeder() {
        accountSeeder.generateAccounts();
        transactionSeeder.generateTransactions();
        customerSeeder();
    }
}
