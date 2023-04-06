package abdhamid.atm.service;

import abdhamid.atm.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements UserDetailsService {
    private final AccountService accountService;
    public static Account currentAccount = null;


    public AuthService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account getCurrentAccount() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String accNumber = auth.getName();
        return accountService.getByAccountNumber(accNumber);
    }

    public static void logout() {
        currentAccount = null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountService.getByAccountNumber(username);
    }
}
