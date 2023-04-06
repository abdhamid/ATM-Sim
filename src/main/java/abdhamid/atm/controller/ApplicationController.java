package abdhamid.atm.controller;

import abdhamid.atm.dto.LoginDto;
import abdhamid.atm.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("currentAccount", authService.getCurrentAccount());
        return "index";
    }
}
