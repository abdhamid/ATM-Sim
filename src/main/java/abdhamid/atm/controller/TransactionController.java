package abdhamid.atm.controller;

import abdhamid.atm.dto.AmountDto;
import abdhamid.atm.dto.TransferDto;
import abdhamid.atm.model.Transaction;
import abdhamid.atm.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static abdhamid.atm.service.AuthService.currentAccount;

@Controller
@RequestMapping("/")
@Slf4j
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
                           RedirectAttributes redirectAttributes) {
        Transaction withdraw = new Transaction();
        String msg = "";
        boolean valid = false;
        log.info("withdraw request");
        try {
            withdraw = transactionService.withdraw(currentAccount, amountDto.getAmount());
            valid = true;
        } catch (Exception e) {
            msg = e.getMessage();
        }

        final RedirectView withdrawView;
        if (valid) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm a");

            withdrawView = new RedirectView("withdraw/summary", true);
            redirectAttributes.addFlashAttribute("amount", withdraw.getAmount());
            redirectAttributes.addFlashAttribute("date", withdraw.getTimestamp().format(format));
            redirectAttributes.addFlashAttribute("customerBalance", currentAccount.getBalance());
        } else {
            withdrawView = new RedirectView("withdraw-other", true);
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

    @GetMapping("withdraw/summary")
    public Object withdrawSummary() {
        return "withdraw-summary";
    }

    @GetMapping("transfer")
    public String transfer(Model model) {
        model.addAttribute("transfer", new TransferDto());
        return "transfer";
    }

    @PostMapping("transfer")
    public Object transfer(@Valid @ModelAttribute TransferDto transferDto,
                           RedirectAttributes redirectAttributes) {

        Transaction transfer = new Transaction();
        String msg = "";

        boolean valid = false;

        try {
            transfer = transactionService.transfer(currentAccount, transferDto);
            valid = true;
        } catch (Exception e) {
            msg = e.getMessage();
        }

        final RedirectView withdrawView;
        if (valid) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("E, dd-MMM-yyyy HH:mm a");

            withdrawView = new RedirectView("transfer/summary", true);
            redirectAttributes.addFlashAttribute("date", transfer.getTimestamp().format(format));
            redirectAttributes.addFlashAttribute("refId", transfer.getId());
            redirectAttributes.addFlashAttribute("receiver", transferDto.getDestinationAccount());
            redirectAttributes.addFlashAttribute("amount", transfer.getAmount());
            redirectAttributes.addFlashAttribute("customerBalance", currentAccount.getBalance().toString());
        } else {
            withdrawView = new RedirectView("transfer", true);
            redirectAttributes.addFlashAttribute("errorStatus", true);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
        }
        return withdrawView;
    }

    @GetMapping("transfer/summary")
    public Object transferSummary() {
        return "transfer-summary";
    }

    @GetMapping("transaction-history")
    public String transactionHistory(Model model, @RequestParam Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Transaction> transactionHistory = transactionService.transactionHistory(currentAccount.getAccountNumber(), pageable);
        model.addAttribute("transactionHistory", transactionHistory);
        model.addAttribute("currentPage", currentPage);
        int totalPages = transactionHistory.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "transaction-history";
    }
}
