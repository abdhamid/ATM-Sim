package abdhamid.atm.service;

import abdhamid.atm.dao.TransactionDao;
import abdhamid.atm.helper.InputValidationHelper;
import abdhamid.atm.helper.TableHelper;
import abdhamid.atm.model.Customer;
import abdhamid.atm.model.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static abdhamid.atm.config.FileConfiguration.TRANSACTION_PATH;

public class TransactionService {
    private final CustomerService customerService;
    private final TransactionDao transactionDao;

    List<Transaction> transactions = new ArrayList<>();

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    static final int MAX_TRANSFER = 1000;
    static final int MIN_TRANSFER = 1;

    public TransactionService(CustomerService customerService) {
        this.customerService = customerService;
        this.transactionDao = new TransactionDao();
        this.transactions = transactionDao.readTransactionCSV(TRANSACTION_PATH);
    }


    //login
    public Customer login(Scanner scanner) {

        String accountNumber = null;
        while (accountNumber == null) {
            System.out.print("Enter Account Number    : ");
            accountNumber = scanner.nextLine();
            accountNumber = InputValidationHelper.validateAccount(accountNumber, "NUMBER");
        }

        String accountPIN = null;
        while (accountPIN == null) {
            System.out.print("Enter PIN   : ");
            accountPIN = scanner.nextLine();
            accountPIN = InputValidationHelper.validateAccount(accountPIN, "PIN");
        }

        Customer customer = customerService.setCurrentCustomer(accountNumber, accountPIN);

        if (customer != null) {
            System.out.println("Login successful");
        } else {
            System.out.println("Invalid Account Number/PIN");
            return null;
        }

        return customer;
    }

    //withdraw
//    public static Transaction withdraw(Scanner scanner, Customer customer) {
//
//        System.out.print("""
//
//                1. $10
//                2. $50
//                3. $100
//                4. Other
//                5. Back
//                Please choose option[5]:\040""");
//        int withdrawOptionNum = 0;
//        String withdrawOption = scanner.nextLine();
//        if (!withdrawOption.matches("[1-5]+") || "".equals(withdrawOption)) {
//            withdrawOptionNum = 5;
//        } else {
//            withdrawOptionNum = Integer.parseInt(withdrawOption);
//        }
//        int withdrawAmount = 0;
//        switch (withdrawOptionNum) {
//            case 1 -> withdrawAmount = 10;
//            case 2 -> withdrawAmount = 50;
//            case 3 -> withdrawAmount = 100;
//            case 4 -> {
//                System.out.println("Other Withdraw\n" +
//                        "Enter amount to withdraw: ");
//                try {
//                    withdrawAmount = scanner.nextInt();
//                } catch (Exception exception) {
//                    System.out.println("Invalid amount");
//                }
//            }
//            default -> System.out.println("Invalid command");
//        }
//        LocalDateTime timestamp = LocalDateTime.now();
//        if (withdrawAmount > 0) {
//            if (withdrawAmount % 10 != 0) System.out.println("Invalid amount");
//            else if (withdrawAmount > 1000) System.out.println("Maximum amount to withdraw is $1000");
//            else if (customer.getBalance() >= withdrawAmount) {
//                customer.setBalance(customer.getBalance() - (double) withdrawAmount);
//                System.out.println("\nSummary\n" +
//                        "Date : " + timestamp.format(dtf) + "\n" +
//                        "Withdraw : " + withdrawAmount + "\n" +
//                        "Balance : " + customer.getBalance());
//                return new Transaction(Transaction.generateRefId(), "DEBIT", customer.getAccountNumber(), (double) withdrawAmount, timestamp);
//            } else {
//                System.out.println("Insufficient balance $" + customer.getBalance());
//            }
//        }
//        return null;
//    }

    public boolean withdraw(Integer withdrawAmount, Customer customer) {
        if (withdrawAmount > 0) {
            if (withdrawAmount % 10 != 0) System.out.println("Invalid amount");
            else if (withdrawAmount > 1000) System.out.println("Maximum amount to withdraw is $1000");
            else if (customer.getBalance() >= withdrawAmount) {
                LocalDateTime timestamp = LocalDateTime.now();
                customer.setBalance(customer.getBalance() - (double) withdrawAmount);
                transactions.add(new Transaction(Transaction.generateRefId(), "DEBIT", customer.getAccountNumber(), (double) withdrawAmount, timestamp));
            } else {
                System.out.println("Insufficient balance $" + customer.getBalance());
                return false;
            }
        } else {
            System.out.println("Invalid amount");
            return false;
        }
        return true;
    }

    public boolean transfer(Customer customer, Customer destCustomer, Integer transferAmount, Integer refNumber) {
        if (customer.getBalance() >= transferAmount) {
            LocalDateTime timestamp = LocalDateTime.now();
            customer.setBalance(customer.getBalance() - transferAmount);
            Transaction outgoing = new Transaction(refNumber, "DEBIT", customer.getAccountNumber(), (double) transferAmount, timestamp);
            destCustomer.setBalance(destCustomer.getBalance() + transferAmount);
            Transaction incoming = new Transaction(refNumber+1, "CREDIT", destCustomer.getAccountNumber(), (double) transferAmount, timestamp);
            transactions.addAll(List.of(incoming, outgoing));
            transactionDao.writeTransactionCSV(transactions, TRANSACTION_PATH);
            System.out.println("\nFund Transfer Summary\n" +
                    "Destination Account : " + destCustomer.getAccountNumber() + "\n" +
                    "Transfer Amount     : $" + transferAmount + "\n" +
                    "Reference Number    : " + String.format("%06d", refNumber) + "\n" +
                    "Balance             : $" + customer.getBalance() + "\n");
            return  true;
        } else {
            System.out.println("Insufficient balance $" + customer.getBalance());
            return false;
        }
    }

    //transfer
    //list 10 last transaction
    public void transactionHistory(Customer customer) {
        List<Transaction> customerTransaction = transactions.stream()
                .filter(transaction -> transaction.getTransactionCreator().equals(customer.getAccountNumber()))
                .limit(10)
                .toList();

        TableHelper table = new TableHelper();
        table.setShowVerticalLines(true);
        table.setHeaders("Reference Number", "Transaction Type", "Account Number", "Amount", "Timestamp");
        for (Transaction transaction : customerTransaction) {
            table.addRow(
                    String.valueOf(transaction.getRefId()),
                    transaction.getTransactionType(),
                    transaction.getTransactionCreator(),
                    String.valueOf(transaction.getAmount()),
                    transaction.getTimestamp().format(dtf));
        }
        table.print();
    }
}
