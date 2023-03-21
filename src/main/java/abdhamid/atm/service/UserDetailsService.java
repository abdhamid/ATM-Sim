package abdhamid.atm.service;

import abdhamid.atm.model.Account;
import abdhamid.atm.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final AccountRepository accountRepository;

    public UserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findCustomerByAccountNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
