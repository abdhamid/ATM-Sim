package abdhamid.atm.controller;

import abdhamid.atm.helper.InputValidationHelper;
import abdhamid.atm.model.Customer;
import abdhamid.atm.service.TransactionService;
import abdhamid.atm.service.CustomerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static abdhamid.atm.helper.RefIdHelper.generateRefId;
import static abdhamid.atm.helper.ScannerHelper.amountScanner;
import static abdhamid.atm.helper.ScannerHelper.customerScanner;

public class ApplicationController {
    private final CustomerService customerService;
    private final TransactionService transactionService;
    static final int MAX_TRANSFER = 1000;
    static final int MIN_TRANSFER = 1;

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

        String option = null;
        while (option == null) {
            System.out.print("""

                    1. Withdraw
                    2. Fund Transfer
                    3. Transaction History
                    4. Exit
                    Please choose option[3]:\040""");
            String input = scanner.nextLine();
            option = InputValidationHelper.validateOption(input, 1, 4);
        }

        switch (Integer.parseInt(option)) {
            case 1 -> withdrawScreen(scanner, customer);
            case 2 -> fundTransferScreen(scanner, customer);
            case 3 -> transactionHistoryScreen(scanner, customer);
            default -> welcomeScreen(scanner);
        }
    }

    public void withdrawScreen(Scanner scanner, Customer customer) {

        String withdrawOption = null;
        while (withdrawOption == null) {
            System.out.print("""
                                            
                    1. $10
                    2. $50
                    3. $100
                    4. Other
                    5. Back
                    Please choose option[5]:\040""");
            String input = scanner.nextLine();
            withdrawOption = InputValidationHelper.validateOption(input, 1, 5);
        }
        switch (Integer.parseInt(withdrawOption)) {
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
        String amountStr = null;
        while (amountStr == null) {
            System.out.println("""

                    Other Withdraw
                    Enter amount to withdraw:\s""");
            String input = scanner.nextLine();
            amountStr = InputValidationHelper.validateTransferAmount(input, MIN_TRANSFER, MAX_TRANSFER);
        }
        int withdrawAmount = Integer.parseInt(amountStr);
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

        String option = null;
        while (option == null) {
            System.out.println("""
                                    
                    1. Transaction\s
                    2. Exit
                    Choose option[2]:""");
            String input = scanner.nextLine();
            option = InputValidationHelper.validateOption(input, 1, 2);
        }

        switch (Integer.parseInt(option)) {
            case 1 -> transactionScreen(scanner, customer);
            case 2 -> welcomeScreen(scanner);
        }

    }

    public void fundTransferScreen(Scanner scanner, Customer customer) {
        Customer destCustomer;

        while (true) {
            String destAccountNum = customerScanner(scanner);
            destCustomer = customerService.getDestinationAccount(customer, destAccountNum);

            if (destCustomer == null) {
                System.out.println("Invalid account");
            } else break;
        }

        int transferAmount = amountScanner(scanner);

        int refNumber = generateRefId();
        String transferOption = null;
        while (transferOption == null) {
            System.out.print("\nTransfer Confirmation\n" +
                    "Destination Account : " + destCustomer.getAccountNumber() + " \n" +
                    "Transfer Amount     : $" + transferAmount + "\n" +
                    "Reference Number    : " + String.format("%06d", refNumber) + "\n" +
                    "\n" +
                    "1. Confirm Trx\n" +
                    "2. Cancel Trx\n" +
                    "Choose option[2]: ");
            String input = scanner.nextLine();
            transferOption = InputValidationHelper.validateOption(input, 1, 3);
        }
        switch (Integer.parseInt(transferOption)) {
            case 1 -> {
                if(transactionService.transfer(customer, destCustomer, transferAmount, refNumber)) {
                    String summaryOption = null;
                    while (summaryOption == null) {
                        System.out.println("""
                                1. Transaction
                                2. Exit
                                Choose option[2]:\s""");
                        String input = scanner.nextLine();
                        summaryOption = InputValidationHelper.validateOption(input, 1, 2);
                    }

                    switch (Integer.parseInt(summaryOption)) {
                        case 1 -> transactionScreen(scanner, customer);
                        case 2 -> welcomeScreen(scanner);
                    }
                } else transactionScreen(scanner, customer);
            }
            case 2 -> fundTransferScreen(scanner, customer);
        }
    }

    private void transactionHistoryScreen(Scanner scanner, Customer customer) {
        transactionService.transactionHistory(customer);
        transactionScreen(scanner, customer);
    }

}
