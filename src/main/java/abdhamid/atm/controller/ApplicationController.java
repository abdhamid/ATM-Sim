package abdhamid.atm.controller;

import abdhamid.atm.dto.LoginDto;
import abdhamid.atm.service.AuthService;
import abdhamid.atm.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

import static abdhamid.atm.service.AuthService.currentAccount;

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

    @GetMapping
    public Object homePage(Model model, HttpSession httpSession) {
        currentAccount = authService.getCurrentAccount();
        httpSession.setAttribute("customerName", currentAccount.getName());
        httpSession.setAttribute("customerBalance", currentAccount.getBalance());
        return "index";
    }


    @GetMapping("logout")
    public Object logout() {
        AuthService.logout();
        return new RedirectView("/login");
    }
}
