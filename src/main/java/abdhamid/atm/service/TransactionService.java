package abdhamid.atm.service;

import abdhamid.atm.dto.TransferDto;
import abdhamid.atm.helper.InputValidationHelper;
import abdhamid.atm.model.Customer;
import abdhamid.atm.model.Transaction;
import abdhamid.atm.repository.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;

import static abdhamid.atm.service.AuthService.currentCustomer;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;

    public TransactionService(TransactionRepository transactionRepository, CustomerService customerService) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
    }

    //withdraw
    @Transactional
    public Transaction withdraw(int withdrawAmount) throws Exception {
        if (InputValidationHelper.validateTransferAmount(String.valueOf(withdrawAmount), 1, 1000).length() > 10) {
            throw new InputMismatchException(InputValidationHelper.validateTransferAmount(String.valueOf(withdrawAmount), 1, 1000));
        }
        
        else if (withdrawAmount > currentCustomer.getBalance()){
            throw new InputMismatchException("Insufficient balance $" + currentCustomer.getBalance());
        }

        currentCustomer.setBalance(currentCustomer.getBalance() - withdrawAmount);
        customerService.save(currentCustomer);
        Transaction withdraw = new Transaction("DEBIT", currentCustomer.getAccountNumber(), (double) withdrawAmount);
        return transactionRepository.save(withdraw);
    }
    //transfer
    @Transactional
    public Transaction transfer(TransferDto transferDto) throws Exception {
        Customer receiver = customerService.findByAccountNumber(transferDto.getAccNumber()).orElseThrow(EntityNotFoundException::new);
        if (InputValidationHelper.validateTransferAmount(String.valueOf(transferDto.getAmount()), 1, 1000).length() > 10) {
            throw new InputMismatchException(InputValidationHelper.validateTransferAmount(String.valueOf(transferDto.getAmount()), 1, 1000));
        }
        if (transferDto.getAmount() > currentCustomer.getBalance()){
            throw new InputMismatchException("Insufficient balance $" + currentCustomer.getBalance());
        }
        currentCustomer.setBalance(currentCustomer.getBalance() - transferDto.getAmount());
        customerService.save(currentCustomer);
        Transaction transfer = new Transaction("DEBIT", currentCustomer.getAccountNumber(), (double) transferDto.getAmount());
        transactionRepository.save(transfer);

        receiver.setBalance(receiver.getBalance() + transferDto.getAmount());
        customerService.save(receiver);
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
