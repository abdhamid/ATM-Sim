package abdhamid.atm.seeder;


import abdhamid.atm.model.Account;
import abdhamid.atm.repository.AccountRepository;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Configuration
public class AccountSeeder {
    private final AccountRepository accountRepository;

    public AccountSeeder(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Async
    public void generateAccounts() {
        Faker faker = new Faker();
        for (int i = 0; i < 200; i++) {
            String name = faker.name().fullName();
            String accountNumber = faker.number().digits(6);
            String pin = faker.number().digits(6);
            Double balance = Double.valueOf(faker.number().numberBetween(0, 1000));
            Account account = new Account(name,pin, balance, accountNumber);
            accountRepository.save(account);
        }

    }
}
