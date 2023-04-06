package abdhamid.atm.service;

import abdhamid.atm.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private AccountService accountService;

    @Mock
    private Authentication authentication;

    @Test
    void getCurrentAccount_willReturnCurrentAccount() throws Exception {
        Account account = new Account();
        account.setAccountNumber("123456");

        when(authentication.getName()).thenReturn("123456");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(accountService.getByAccountNumber("123456")).thenReturn(account);

        assertEquals(account, authService.getCurrentAccount());
        verify(accountService, times(1)).getByAccountNumber("123456");
    }

    @Test
    void logout_willSetCurrentAccountToNull() throws Exception {
        authService.currentAccount = new Account();
        AuthService.logout();
        assertEquals(null, authService.currentAccount);
    }

    @Test
    void loadByUsername_willReturnAccount() throws Exception {
        Account account = new Account("Ben", "123456", 100.0, "123456");
        when(accountService.getByAccountNumber(account.getAccountNumber())).thenReturn(account);

        var res = authService.loadUserByUsername("123456");

        assertEquals(account.getAccountNumber(), res.getUsername());
        assertEquals(account.getPin(), res.getPassword());
    }
}