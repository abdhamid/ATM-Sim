package abdhamid.atm.seeder;

import abdhamid.atm.model.Account;
import abdhamid.atm.model.Transaction;
import abdhamid.atm.repository.TransactionRepository;
import abdhamid.atm.service.AccountService;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;


@Configuration
public class TransactionSeeder {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    public TransactionSeeder(AccountService accountService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @Async
    public void generateTransactions() {
        var counter = 50;
        Faker faker  = new Faker();
        while (counter > 0) {
            Account sender =  accountService.getById(faker.number().numberBetween(1, 100));
            int amount = faker.number().numberBetween(1, 100);
            if (sender != null && sender.getBalance() > amount) {
                Transaction transaction = new Transaction("DEBIT", sender.getAccountNumber(), Double.valueOf(amount));
                transactionRepository.save(transaction);
                counter--;
            }
        }
    }
}
