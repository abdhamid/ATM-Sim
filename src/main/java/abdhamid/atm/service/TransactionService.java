package abdhamid.atm.service;

import abdhamid.atm.dto.TransferDto;
import abdhamid.atm.helper.InputValidationHelper;
import abdhamid.atm.model.Customer;
import abdhamid.atm.model.Transaction;
import abdhamid.atm.repository.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.InputMismatchException;
import java.util.List;

import static abdhamid.atm.service.AuthService.currentCustomer;

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

        currentCustomer.setBalance(currentCustomer.getBalance() - withdrawAmount);
        accountService.save(currentCustomer);
        Transaction withdraw = new Transaction("DEBIT", currentCustomer.getAccountNumber(), (double) withdrawAmount);
        return transactionRepository.save(withdraw);
    }

    private void validateWithdrawAmount(int withdrawAmount) {
        boolean isValidWithdrawAmount = InputValidationHelper.validateTransferAmount(String.valueOf(withdrawAmount), 1, 1000).length() > 10;
        if (isValidWithdrawAmount) {
            throw new InputMismatchException(InputValidationHelper.validateTransferAmount(String.valueOf(withdrawAmount), 1, 1000));
        }

        if (withdrawAmount > currentCustomer.getBalance()){
            throw new InputMismatchException("Insufficient balance $" + currentCustomer.getBalance());
        }
    }

    //transfer
    @Transactional
    public Transaction transfer(TransferDto transferDto) {
        Customer receiver = accountService.getByAccountNumber(transferDto.getDestinationAccount());

        validateWithdrawAmount(transferDto.getAmount());

        currentCustomer.setBalance(currentCustomer.getBalance() - transferDto.getAmount());
        accountService.save(currentCustomer);
        Transaction transfer = new Transaction("DEBIT", currentCustomer.getAccountNumber(), (double) transferDto.getAmount());
        transactionRepository.save(transfer);

        receiver.setBalance(receiver.getBalance() + transferDto.getAmount());
        accountService.save(receiver);
        Transaction receive = new Transaction("CREDIT", receiver.getAccountNumber(), (double) transferDto.getAmount());
        transactionRepository.save(receive);

        return transfer;
    }

    //get 10 last transaction
    public List<Transaction> transactionHistory() {
        return transactionRepository.findByAccNumberDesc(currentCustomer.getAccountNumber(), PageRequest.of(0, 10))
                .stream().toList();
    }
    //save to db
}
