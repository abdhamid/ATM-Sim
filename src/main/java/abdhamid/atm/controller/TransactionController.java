package abdhamid.atm.controller;

import abdhamid.atm.dto.AmountDto;
import abdhamid.atm.dto.TransferDto;
import abdhamid.atm.model.Transaction;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static abdhamid.atm.helper.InputValidationHelper.validateTransferAmount;
import static abdhamid.atm.service.AuthService.currentCustomer;

@Controller
@RequestMapping("/")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("withdraw")
    public String withdraw(Model model) {
        model.addAttribute("amount", new AmountDto());
        return "withdraw";
    }

    @PostMapping("withdraw")
    public Object withdraw(@Valid @ModelAttribute("amount") AmountDto amountDto,
                           HttpSession httpSession,
                           RedirectAttributes redirectAttributes) {
        int amount = Integer.parseInt(validateTransferAmount(String.valueOf(amountDto.getAmount()), 1, 1000));
        Transaction withdraw = new Transaction();
        String msg = "";
        boolean valid = false;
        try {
            withdraw = transactionService.withdraw(amount);
            valid = true;
        } catch (Exception e) {
            msg = e.getMessage();
        }



        final RedirectView withdrawView;
        if (valid) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm a");

            withdrawView = new RedirectView("/withdraw/summary", true);
            redirectAttributes.addFlashAttribute("amount", withdraw.getAmount());
            redirectAttributes.addFlashAttribute("date", withdraw.getTimestamp().format(format));
            httpSession.setAttribute("customerBalance", currentCustomer.getBalance());
        } else {
            withdrawView = new RedirectView("/withdraw-other", true);
            redirectAttributes.addFlashAttribute("errorStatus", true);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
        }
        return withdrawView;

    }

    @GetMapping("withdraw-other")
    public String withdrawOther(Model model) {
        model.addAttribute("amount", new AmountDto());
        return "withdraw-other";
    }

    @PostMapping("withdraw-other")
    public Object withdrawOther(@Valid @ModelAttribute("amount") AmountDto amountDto,
                                HttpSession httpSession,
                                RedirectAttributes redirectAttributes) {
        Transaction withdraw = new Transaction();
        String msg = "";

        boolean valid = false;

        try {
            withdraw = transactionService.withdraw(amountDto.getAmount());
            valid = true;
        } catch (Exception e) {
            msg = e.getMessage();
        }

        final RedirectView withdrawView;
        if (valid) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm a");

            withdrawView = new RedirectView("/withdraw/summary", true);
            redirectAttributes.addFlashAttribute("amount", withdraw.getAmount());
            redirectAttributes.addFlashAttribute("date", withdraw.getTimestamp().format(format));
            httpSession.setAttribute("customerBalance", currentCustomer.getBalance());
        } else {
            withdrawView = new RedirectView("/withdraw", true);
            redirectAttributes.addFlashAttribute("errorStatus", true);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
        }
        return withdrawView;

    }

    @GetMapping("withdraw/summary")
    public Object withdrawSummary(){
        if (AuthService.isAuthenticated) {
            return "withdraw-summary";
        }
        return new RedirectView("/login", true);
    }

    @GetMapping("transfer")
    public String transfer(Model model) {
        model.addAttribute("transfer", new TransferDto());
        return "transfer";
    }

    @PostMapping("transfer")
    public Object transfer(@Valid @ModelAttribute TransferDto transferDto,
                           RedirectAttributes redirectAttributes,
                           HttpSession httpSession) {

        Transaction transfer = new Transaction();
        String msg = "";

        boolean valid = false;

        try {
            transfer = transactionService.transfer(transferDto);
            valid = true;
        } catch (Exception e) {
            msg = e.getMessage();
        }

        final RedirectView withdrawView;
        if (valid) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm a");

            withdrawView = new RedirectView("/transfer/summary", true);
            redirectAttributes.addFlashAttribute("date", transfer.getTimestamp().format(format));
            redirectAttributes.addFlashAttribute("refId", transfer.getId());
            redirectAttributes.addFlashAttribute("receiver", transferDto.getDestinationAccount());
            redirectAttributes.addFlashAttribute("amount", transfer.getAmount());
            System.out.println(currentCustomer);
            redirectAttributes.addFlashAttribute("customerBalance", currentCustomer.getBalance().toString());
        } else {
            withdrawView = new RedirectView("/transfer", true);
            redirectAttributes.addFlashAttribute("errorStatus", true);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
        }
        return withdrawView;
    }


    @GetMapping("transfer/summary")
    public Object transferSummary(){
        if (AuthService.isAuthenticated) {
            return "transfer-summary";
        }
        return new RedirectView("/login", true);
    }

    @GetMapping("transaction-history")
    public String transactionHistory(Model model) {
        if (AuthService.isAuthenticated){
            List<Transaction> transactionHistory = transactionService.transactionHistory();
            model.addAttribute("No", transactionHistory.size());
            model.addAttribute("history", transactionHistory);
            return "transaction-history";
        } else {
            return "login";
        }
    }
}
