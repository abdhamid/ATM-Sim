package abdhamid.atm.service;

import abdhamid.atm.dto.LoginDto;
import abdhamid.atm.helper.InputValidationHelper;
import abdhamid.atm.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class AuthService {
    private final AccountService accountService;
    public static boolean isAuthenticated = false;

    public static Account currentAccount = null;


    public AuthService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account login(LoginDto loginDto) throws LoginException {
        if (!InputValidationHelper.validateAccountNumber(loginDto.getAccNumber()).equals("success")){
            throw new LoginException(InputValidationHelper.validateAccountNumber(loginDto.getAccNumber()));
        } else if (!InputValidationHelper.validateAccountPIN(loginDto.getAccPin()).equals("success")) {
            throw new LoginException(InputValidationHelper.validateAccountPIN(loginDto.getAccPin()));
        }
        Account account = accountService.findByAccountNumber(loginDto.getAccNumber()).orElseThrow(() -> new LoginException("Invalid Account Number/PIN"));
        if (account.getPin().equals(loginDto.getAccPin())) {
            AuthService.isAuthenticated = true;
            currentAccount = account;
            return account;
        } else {
            throw new LoginException("Invalid Account Number/PIN");
        }
    }

    public Account getCurrentAccount() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String accNumber = auth.getName();
        return accountService.getByAccountNumber(accNumber);
    }

    public static void logout() {
        currentAccount = null;
        AuthService.isAuthenticated = false;
    }
}
