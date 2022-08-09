package abdhamid.atm.service;

import abdhamid.atm.dto.LoginDto;
import abdhamid.atm.helper.InputValidationHelper;
import abdhamid.atm.model.Customer;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class AuthService {
    private final CustomerService customerService;
    public static boolean isAuthenticated = false;

    public static Customer currentCustomer = null;

    public AuthService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer login(LoginDto loginDto) throws LoginException {
        if (!InputValidationHelper.validateAccountNumber(loginDto.getAccNumber()).equals("success")){
            throw new LoginException(InputValidationHelper.validateAccountNumber(loginDto.getAccNumber()));
        } else if (!InputValidationHelper.validateAccountPIN(loginDto.getAccPin()).equals("success")) {
            throw new LoginException(InputValidationHelper.validateAccountPIN(loginDto.getAccPin()));
        }
        Customer customer = customerService.findByAccountNumber(loginDto.getAccNumber()).orElseThrow(() -> new LoginException("Invalid Account Number/PIN"));
        if (customer.getPin().equals(loginDto.getAccPin())) {
            AuthService.isAuthenticated = true;
            currentCustomer = customer;
            return customer;
        } else {
            throw new LoginException("Invalid Account Number/PIN");
        }
    }

    public static void logout() {
        currentCustomer = null;
        AuthService.isAuthenticated = false;
    }
}
