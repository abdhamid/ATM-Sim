package abdhamid.atm.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import abdhamid.atm.dto.LoginDto;

import javax.security.auth.login.LoginException;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthService.class})
@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @MockBean
    private AccountService accountService;

    /**
     * Method under test: {@link AuthService#login(LoginDto)}
     */
    @Test
    void testLogin() throws LoginException {
        assertThrows(LoginException.class, () -> authService.login(new LoginDto("42", "Acc Pin")));
    }

    /**
     * Method under test: {@link AuthService#login(LoginDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin2() throws LoginException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "input" is null
        //       at abdhamid.atm.helper.InputValidationHelper.validateAccountNumber(InputValidationHelper.java:54)
        //       at abdhamid.atm.service.AuthService.login(AuthService.java:22)
        //   In order to prevent login(LoginDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   login(LoginDto).
        //   See https://diff.blue/R013 to resolve this issue.

        authService.login(new LoginDto());
    }

    /**
     * Method under test: {@link AuthService#login(LoginDto)}
     */
    @Test
    void testLogin3() throws LoginException {
        LoginDto loginDto = mock(LoginDto.class);
        when(loginDto.getAccNumber()).thenReturn("42");
        assertThrows(LoginException.class, () -> authService.login(loginDto));
        verify(loginDto, atLeast(1)).getAccNumber();
    }

    /**
     * Method under test: {@link AuthService#login(LoginDto)}
     */
    @Test
    void testLogin4() throws LoginException {
        LoginDto loginDto = new LoginDto("42", "Acc Pin");
        loginDto.setAccNumber("424242");
        assertThrows(LoginException.class, () -> authService.login(loginDto));
    }

    /**
     * Method under test: {@link AuthService#login(LoginDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin5() throws LoginException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "input" is null
        //       at abdhamid.atm.helper.InputValidationHelper.validateAccountPIN(InputValidationHelper.java:64)
        //       at abdhamid.atm.service.AuthService.login(AuthService.java:24)
        //   In order to prevent login(LoginDto)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   login(LoginDto).
        //   See https://diff.blue/R013 to resolve this issue.

        LoginDto loginDto = new LoginDto();
        loginDto.setAccNumber("424242");
        authService.login(loginDto);
    }

    /**
     * Method under test: {@link AuthService#logout()}
     */
    @Test
    void testLogout() {
        // TODO: Complete this test.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   There are no fields that could be asserted on.

        AuthService.logout();
    }
}

