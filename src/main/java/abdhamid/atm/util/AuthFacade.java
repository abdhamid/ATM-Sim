package abdhamid.atm.util;

import abdhamid.atm.model.Account;
import abdhamid.atm.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final AccountRepository accountRepository;

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Account getCurrentUser() {
        String accNumber = getAuth().getName();
        return accountRepository.findCustomerByAccountNumber(accNumber)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
