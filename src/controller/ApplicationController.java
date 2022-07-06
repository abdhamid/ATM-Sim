package controller;

import helper.InputValidationHelper;
import model.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static helper.RefIdHelper.generateRefId;

public class ApplicationController {
    static final int MAX_TRANSFER = 1000;
    static final int MIN_TRANSFER = 1;
    public static void welcomeScreen(Scanner scanner, List<Customer> customers) {
        while (true) {
            String accountNumber = null;
            while (accountNumber==null) {
                System.out.print("Enter Account Number    : ");
                accountNumber = scanner.nextLine();
                accountNumber = InputValidationHelper.validateAccount(accountNumber, "NUMBER");
            }

            String accountPIN = null;
            while (accountPIN==null) {
                System.out.print("Enter PIN   : ");
                accountPIN = scanner.nextLine();
                accountPIN = InputValidationHelper.validateAccount(accountPIN, "PIN");
            }

            String finalAccountNumber = accountNumber;
            String finalAccountPIN = accountPIN;
            Customer customer = customers.stream()
                    .filter(cust -> Objects.equals(cust.getAccountNumber(), finalAccountNumber))
                    .filter(cust -> Objects.equals(cust.getPin(), finalAccountPIN))
                    .findFirst()
                    .orElse(null);

            if (customer != null) {
                System.out.println("Login successful");
                transactionScreen(scanner, customers, customer);
            } else {
                System.out.println("Invalid Account Number/PIN");
            }
        }
    }

    public static void transactionScreen(Scanner scanner, List<Customer> customers, Customer customer) {

        String option = null;
        while (option==null) {
            System.out.print("""

                1. Withdraw
                2. Fund Transfer
                3. Exit
                Please choose option[3]:\040""");
            String input = scanner.nextLine();
            option = InputValidationHelper.validateOption(input, 1, 3);
        }

        switch (Integer.parseInt(option)) {
            case 1 -> withdrawScreen(scanner, customers, customer);
            case 2 -> fundTransferScreen(scanner, customers, customer);
            case 3 -> welcomeScreen(scanner, customers);
        }
    }

    public static void withdrawScreen(Scanner scanner, List<Customer> customers, Customer customer) {
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
        int withdrawAmount = 0;
        switch (Integer.parseInt(withdrawOption)) {
            case 1 -> withdrawAmount = 10;
            case 2 -> withdrawAmount = 50;
            case 3 -> withdrawAmount = 100;
            case 4 -> {
                String amountStr = null;
                while (amountStr==null) {
                    System.out.println("""

                            Other Withdraw
                            Enter amount to withdraw:\s""");
                    String input = scanner.nextLine();
                    amountStr = InputValidationHelper.validateTransferAmount(input, MIN_TRANSFER, MAX_TRANSFER);
                }
                withdrawAmount = Integer.parseInt(amountStr);
            }
            case 5 -> transactionScreen(scanner, customers, customer);
            default -> System.out.println("Unknown command");
        }
        if (withdrawAmount > 0) {
            if (withdrawAmount % 10 != 0) System.out.println("Invalid amount");
            else if (withdrawAmount > 1000) System.out.println("Maximum amount to withdraw is $1000");
            else if (customer.getBalance() >= withdrawAmount) {
                customer.setBalance(customer.getBalance() - (double) withdrawAmount);
                summaryScreen(scanner, customers, customer, withdrawAmount);
            } else {
                System.out.println("Insufficient balance $" + customer.getBalance());
            }
        } else {
            System.out.println("Invalid amount");
        }
    }

    public static void summaryScreen(Scanner scanner, List<Customer> customers, Customer customer, int withdrawAmount) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        System.out.println("\nSummary\n" +
                "Date : " + LocalDateTime.now().format(dtf) + "\n" +
                "Withdraw : " + withdrawAmount + "\n" +
                "Balance : " + customer.getBalance());

        String option = null;
        while (option==null) {
            System.out.println("""
                                    
                    1. Transaction\s
                    2. Exit
                    Choose option[2]:""");
            String input = scanner.nextLine();
            option = InputValidationHelper.validateOption(input, 1, 2);
        }

        switch (Integer.parseInt(option)) {
            case 1 -> transactionScreen(scanner, customers, customer);
            case 2 -> welcomeScreen(scanner, customers);
        }

    }

    public static void fundTransferScreen(Scanner scanner, List<Customer> customers, Customer customer) {
        Customer destCustomer;
        while (true) {
            System.out.print("" +
                    "Please enter destination account and \n" +
                    "press enter to continue : ");

            String destAccountNum = scanner.nextLine();

            if (!destAccountNum.matches("[0-9]+")) {
                System.out.println("Invalid account");
            } else {
                destCustomer = customers.stream()
                        .filter(cust -> Objects.equals(cust.getAccountNumber(), destAccountNum))
                        .findFirst()
                        .orElse(null);

                if (destCustomer == null) {
                    System.out.println("Invalid account");
                } else break;
            }
        }

        String transferAmountInput = null;
        while (transferAmountInput==null) {
            System.out.print("Please enter transfer amount and press enter to continue : ");
            String input = scanner.nextLine();
            transferAmountInput = InputValidationHelper.validateTransferAmount(input, MIN_TRANSFER, MAX_TRANSFER);
        }
        int transferAmount = Integer.parseInt(transferAmountInput);

        int refNumber = generateRefId();
        String transferOption = null;
        while (transferOption==null) {
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
                if (customer.getBalance() >= transferAmount) {
                    customer.setBalance(customer.getBalance() - transferAmount);
                    destCustomer.setBalance(destCustomer.getBalance() + transferAmount);
                    System.out.println("\nFund Transfer Summary\n" +
                            "Destination Account : " + destCustomer.getAccountNumber() + "\n" +
                            "Transfer Amount     : $" + transferAmount + "\n" +
                            "Reference Number    : " + String.format("%06d", refNumber) + "\n" +
                            "Balance             : $" + customer.getBalance() + "\n");

                    String summaryOption = null;
                    while (summaryOption==null) {
                        System.out.println("""
                                1. Transaction
                                2. Exit
                                Choose option[2]:\s""");
                        String input = scanner.nextLine();
                        summaryOption = InputValidationHelper.validateOption(input, 1, 2);
                    }

                    switch (Integer.parseInt(summaryOption)) {
                        case 1 -> transactionScreen(scanner, customers, customer);
                        case 2 -> welcomeScreen(scanner, customers);
                    }
                } else {
                    System.out.println("Insufficient balance $" + customer.getBalance());
                }
            }
            case 2 -> fundTransferScreen(scanner, customers, customer);
        }
    }

}
