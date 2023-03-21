package abdhamid.atm.service;

import abdhamid.atm.model.Account;
import abdhamid.atm.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> findByAccountNumber(String accNumber) {
        return accountRepository.findCustomerByAccountNumber(accNumber);
    }

    public Account getByAccountNumber(String destinationAccount) {
        return accountRepository.findCustomerByAccountNumber(destinationAccount).orElseThrow(() -> new EntityNotFoundException("Invalid account"));
    }
}
