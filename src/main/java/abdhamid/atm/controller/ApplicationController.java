package abdhamid.atm.controller;

import abdhamid.atm.model.Customer;
import abdhamid.atm.service.TransactionService;
import abdhamid.atm.service.CustomerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static abdhamid.atm.helper.RefIdHelper.generateRefId;
import static abdhamid.atm.helper.ScannerHelper.*;

public class ApplicationController {
    private final CustomerService customerService;
    private final TransactionService transactionService;

    public ApplicationController() {
        this.customerService = new CustomerService();
        this.transactionService = new TransactionService(customerService);
    }

    public void welcomeScreen(Scanner scanner) {
        Customer customer = null;
        while (customer == null) {
            customer = transactionService.login(scanner);
        }
        transactionScreen(scanner, customer);
    }

    public void transactionScreen(Scanner scanner, Customer customer) {
        String transactionMenu = """

                    1. Withdraw
                    2. Fund Transfer
                    3. Transaction History
                    4. Exit
                    Please choose option[3]:\040""";

        int option = optionScanner(scanner, 1, 4, transactionMenu);

        switch (option) {
            case 1 -> withdrawScreen(scanner, customer);
            case 2 -> fundTransferScreen(scanner, customer);
            case 3 -> transactionHistoryScreen(scanner, customer);
            default -> welcomeScreen(scanner);
        }
    }

    public void withdrawScreen(Scanner scanner, Customer customer) {
        String withdrawMenu = """
                                            
                    1. $10
                    2. $50
                    3. $100
                    4. Other
                    5. Back
                    Please choose option[5]:\040""";
        int withdrawOption = optionScanner(scanner, 1, 5, withdrawMenu);

        switch (withdrawOption) {
            case 1 -> {
                if (transactionService.withdraw(10, customer)) {
                    summaryScreen(scanner, customer, 10);
                } else transactionScreen(scanner, customer);
            }
            case 2 -> {
                if (transactionService.withdraw(50, customer)) {
                    summaryScreen(scanner, customer, 50);
                } else transactionScreen(scanner, customer);
            }
            case 3 -> {
                if (transactionService.withdraw(100, customer)) {
                    summaryScreen(scanner, customer, 100);
                } else transactionScreen(scanner, customer);
            }
            case 4 -> otherWithdrawScreen(scanner, customer);
            default -> transactionScreen(scanner, customer);

        }
    }

    public void otherWithdrawScreen(Scanner scanner, Customer customer) {
        String withdrawAmountMenu = """

                      Other Withdraw
                      Enter amount to withdraw:\s""";
        int withdrawAmount = amountScanner(scanner, withdrawAmountMenu);

        if (transactionService.withdraw(withdrawAmount, customer)) {
            summaryScreen(scanner, customer, withdrawAmount);
        } else transactionScreen(scanner, customer);
    }

    public void summaryScreen(Scanner scanner, Customer customer, int withdrawAmount) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        System.out.println("\nSummary\n" +
                "Date : " + LocalDateTime.now().format(dtf) + "\n" +
                "Withdraw : " + withdrawAmount + "\n" +
                "Balance : " + customer.getBalance());

        String summaryMenu = """
                                    
                    1. Transaction\s
                    2. Exit
                    Choose option[2]:""";
        int summaryOption = optionScanner(scanner, 1, 2, summaryMenu);

        switch (summaryOption) {
            case 1 -> transactionScreen(scanner, customer);
            case 2 -> welcomeScreen(scanner);
        }

    }

    public void fundTransferScreen(Scanner scanner, Customer customer) {
        Customer destCustomer;
        String fundTransferCustomerMenu = """
                          
                          Please enter destination account and\s
                          press enter to continue :\s""";
        while (true) {
            String destAccountNum = customerScanner(scanner, fundTransferCustomerMenu);
            destCustomer = customerService.getDestinationAccount(customer, destAccountNum);

            if (destCustomer == null) {
                System.out.println("Invalid account");
            } else break;
        }
        String fundTransferAmountMenu = "\nPlease enter transfer amount and press enter to continue : ";
        int transferAmount = amountScanner(scanner, fundTransferAmountMenu);

        int refNumber = generateRefId();

        String fundTransferConfirmationMenu = "\nTransfer Confirmation\n" +
                "Destination Account : " + destCustomer.getAccountNumber() + " \n" +
                "Transfer Amount     : $" + transferAmount + "\n" +
                "Reference Number    : " + String.format("%06d", refNumber) + "\n" +
                "\n" +
                "1. Confirm Trx\n" +
                "2. Cancel Trx\n" +
                "Choose option[2]: ";
        int transferOption = optionScanner(scanner, 1, 3, fundTransferConfirmationMenu);

        switch (transferOption) {
            case 1 -> {
                if(transactionService.transfer(customer, destCustomer, transferAmount, refNumber)) {
                    String transferSummaryMenu = """
                                1. Transaction
                                2. Exit
                                Choose option[2]:\s""";
                    int transferSummaryOption = optionScanner(scanner, 1, 2, transferSummaryMenu);

                    switch (transferSummaryOption) {
                        case 1 -> transactionScreen(scanner, customer);
                        default -> welcomeScreen(scanner);
                    }
                } else transactionScreen(scanner, customer);
            }
            default -> fundTransferScreen(scanner, customer);
        }
    }

    private void transactionHistoryScreen(Scanner scanner, Customer customer) {
        transactionService.transactionHistory(customer);
        transactionScreen(scanner, customer);
    }

}
