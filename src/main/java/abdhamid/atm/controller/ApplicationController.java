package abdhamid.atm.controller;

import abdhamid.atm.dto.LoginDto;
import abdhamid.atm.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;


import static abdhamid.atm.service.AuthService.currentAccount;

@Controller
@RequestMapping("/")
public class ApplicationController {
    private final AuthService authService;


    public ApplicationController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("login", new LoginDto());
        return "login";
    }

    @GetMapping
    public Object homePage(Model model) {
        currentAccount = authService.getCurrentAccount();
        model.addAttribute("currentAccount", currentAccount);
        return "index";
    }


    @GetMapping("logout")
    public Object logout() {
        AuthService.logout();
        return new RedirectView("/login");
    }
}
