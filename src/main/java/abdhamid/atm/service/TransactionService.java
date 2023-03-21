package abdhamid.atm.service;

import abdhamid.atm.dto.TransferDto;
import abdhamid.atm.helper.InputValidationHelper;
import abdhamid.atm.model.Account;
import abdhamid.atm.model.Transaction;
import abdhamid.atm.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.InputMismatchException;

import static abdhamid.atm.service.AuthService.currentAccount;

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
    public Transaction withdraw(int withdrawAmount) throws Exception {
        validateWithdrawAmount(withdrawAmount);

        currentAccount.setBalance(currentAccount.getBalance() - withdrawAmount);
        accountService.save(currentAccount);
        Transaction withdraw = new Transaction("DEBIT", currentAccount.getAccountNumber(), (double) withdrawAmount);
        return transactionRepository.save(withdraw);
    }

    private void validateWithdrawAmount(int withdrawAmount) {
        boolean isValidWithdrawAmount = InputValidationHelper.validateTransferAmount(String.valueOf(withdrawAmount), 1, 1000).length() > 10;
        if (isValidWithdrawAmount) {
            throw new InputMismatchException(InputValidationHelper.validateTransferAmount(String.valueOf(withdrawAmount), 1, 1000));
        }

        if (withdrawAmount > currentAccount.getBalance()){
            throw new InputMismatchException("Insufficient balance $" + currentAccount.getBalance());
        }
    }

    //transfer
    @Transactional
    public Transaction transfer(TransferDto transferDto) {
        Account receiver = accountService.getByAccountNumber(transferDto.getDestinationAccount());

        validateWithdrawAmount(transferDto.getAmount());

        currentAccount.setBalance(currentAccount.getBalance() - transferDto.getAmount());
        accountService.save(currentAccount);
        Transaction transfer = new Transaction("DEBIT", currentAccount.getAccountNumber(), (double) transferDto.getAmount());
        transactionRepository.save(transfer);

        receiver.setBalance(receiver.getBalance() + transferDto.getAmount());
        accountService.save(receiver);
        Transaction receive = new Transaction("CREDIT", receiver.getAccountNumber(), (double) transferDto.getAmount());
        transactionRepository.save(receive);

        return transfer;
    }

    //get 10 last transaction
    public Page<Transaction> transactionHistory(String accNumber, Pageable page) {
        return transactionRepository.findByAccNumberDesc(accNumber, page);
    }
    //save to db
}
