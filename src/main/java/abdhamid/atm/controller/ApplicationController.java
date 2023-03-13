package abdhamid.atm.controller;

import abdhamid.atm.dto.LoginDto;
import abdhamid.atm.service.AuthService;
import abdhamid.atm.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static abdhamid.atm.service.AuthService.currentCustomer;

@Controller
@RequestMapping("/")
public class ApplicationController {
    private final TransactionService transactionService;
    private final AuthService authService;


    public ApplicationController(TransactionService transactionService,
                                 AuthService authService) {
        this.transactionService = transactionService;
        this.authService = authService;
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("login", new LoginDto());
        return "login";
    }

    @PostMapping("login")
    public Object loginPost(@Valid @ModelAttribute LoginDto loginDto,
                            RedirectAttributes redirectAttributes) {
        String msg;
        try {
            authService.login(loginDto);
            return new RedirectView("");
        } catch (LoginException e) {
            msg = e.getMessage();
        }

        redirectAttributes.addFlashAttribute("errorStatus", true);
        redirectAttributes.addFlashAttribute("errorMessage", msg);
        return new RedirectView("/login");
    }

    @GetMapping
    public Object homePage(Model model, HttpSession httpSession) {
        if (AuthService.isAuthenticated) {
            httpSession.setAttribute("customerName", currentCustomer.getName());
            httpSession.setAttribute("customerBalance", currentCustomer.getBalance());
            return "index";
        } else {
            return new RedirectView("/login");
        }
    }



    @GetMapping("logout")
    public Object logout() {
        AuthService.logout();
        return new RedirectView("/login");
    }
}
