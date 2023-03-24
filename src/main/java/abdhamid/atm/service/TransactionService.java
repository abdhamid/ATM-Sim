package abdhamid.atm.service;

import abdhamid.atm.dto.TransferDto;
import abdhamid.atm.model.Account;
import abdhamid.atm.model.Transaction;
import abdhamid.atm.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.InputMismatchException;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    //withdraw
    @Transactional
    public Transaction withdraw(Account account, int withdrawAmount) throws Exception {
        validateTransactionAmount(account, withdrawAmount);

        account.setBalance(account.getBalance() - withdrawAmount);
        accountService.save(account);
        Transaction withdraw = new Transaction("DEBIT", account.getAccountNumber(), (double) withdrawAmount);
        transactionRepository.save(withdraw);
        return withdraw;
    }

    //transfer

    @Transactional
    public Transaction transfer(Account sender, TransferDto transferDto) {
        Account receiver = accountService.getByAccountNumber(transferDto.getDestinationAccount());

        validateTransactionAmount(sender, transferDto.getAmount());

        sender.setBalance(sender.getBalance() - transferDto.getAmount());
        accountService.save(sender);
        Transaction transfer = new Transaction("DEBIT", sender.getAccountNumber(), (double) transferDto.getAmount());
        transactionRepository.save(transfer);

        receiver.setBalance(receiver.getBalance() + transferDto.getAmount());
        accountService.save(receiver);
        Transaction receive = new Transaction("CREDIT", receiver.getAccountNumber(), (double) transferDto.getAmount());
        transactionRepository.save(receive);

        return transfer;
    }
    //get transaction history

    public Page<Transaction> transactionHistory(String accNumber, Pageable page) {
        return transactionRepository.findByAccNumberDesc(accNumber, page);
    }

    private void validateTransactionAmount(Account account, int transactionAmount) {
        boolean isValidTransactionAmount = validateTransactionAmount(String.valueOf(transactionAmount), 1, 1000).length() > 10;
        if (isValidTransactionAmount) {
            throw new InputMismatchException(validateTransactionAmount(String.valueOf(transactionAmount), 1, 1000));
        }

        if (transactionAmount > account.getBalance()){
            throw new InputMismatchException("Insufficient balance $" + account.getBalance());
        }
    }

    public String validateTransactionAmount(String amount, int min, int max) {
        if (!amount.matches("\\d+")) {
            return "Invalid amount";
        }
        else if (Integer.parseInt(amount)%10 != 0) {
            return "Amount must be in a multiple of 10";
        }
        else if (Integer.parseInt(amount) > max) {
            return "Maximum amount to transfer is $1000";
        }
        else if (Integer.parseInt(amount) < min) {
            return "Minimum amount to transfer is $1";
        }
        else return amount;
    }
}
