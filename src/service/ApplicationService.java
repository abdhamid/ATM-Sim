package service;

import helper.TableHelper;
import model.Customer;
import model.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ApplicationService {
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");


    //login
    public static Customer login(Scanner scanner, List<Customer> customers, Customer customer) {
        while (customer == null) {
            System.out.print("Enter Account Number    : ");
            String accountNumber = scanner.nextLine();

            if (accountNumber.length() != 6) {
                System.out.println("Account Number should have 6 digits length");
                continue;
            }
            if (accountNumber.contains("[a-zA-Z]+")) {
                System.out.println("Account Number should only contains numbers");
            }

            System.out.print("Enter PIN   : ");
            String accountPIN = scanner.nextLine();

            if (accountPIN.length() != 6) {
                System.out.println("PIN should have 6 digits length");
            }
            if (accountPIN.contains("[a-zA-Z]+")) {
                System.out.println("PIN should only contains numbers");
                continue;
            }

            customer = customers.stream()
                    .filter(cust -> accountNumber.equals(cust.getAccountNumber()))
                    .filter(cust -> accountPIN.equals(cust.getPin()))
                    .findFirst()
                    .orElse(null);

            if (customer != null) {
                System.out.println("Login successful");
            } else {
                System.out.println("Invalid Account Number/PIN");
            }
        }
        return customer;
    }

    //withdraw
    public static Transaction withdraw(Scanner scanner, Customer customer) {

        System.out.print("""

                1. $10
                2. $50
                3. $100
                4. Other
                5. Back
                Please choose option[5]:\040""");
        int withdrawOptionNum = 0;
        String withdrawOption = scanner.nextLine();
        if (!withdrawOption.matches("[1-5]+") || "".equals(withdrawOption)) {
            withdrawOptionNum = 5;
        } else {
            withdrawOptionNum = Integer.parseInt(withdrawOption);
        }
        int withdrawAmount = 0;
        switch (withdrawOptionNum) {
            case 1 -> withdrawAmount = 10;
            case 2 -> withdrawAmount = 50;
            case 3 -> withdrawAmount = 100;
            case 4 -> {
                System.out.println("Other Withdraw\n" +
                        "Enter amount to withdraw: ");
                try {
                    withdrawAmount = scanner.nextInt();
                } catch (Exception exception) {
                    System.out.println("Invalid amount");
                }
            }
            default -> System.out.println("Invalid command");
        }
        LocalDateTime timestamp = LocalDateTime.now();
        if (withdrawAmount > 0) {
            if (withdrawAmount % 10 != 0) System.out.println("Invalid amount");
            else if (withdrawAmount > 1000) System.out.println("Maximum amount to withdraw is $1000");
            else if (customer.getBalance() >= withdrawAmount) {
                customer.setBalance(customer.getBalance() - (double) withdrawAmount);
                System.out.println("\nSummary\n" +
                        "Date : " + timestamp.format(dtf) + "\n" +
                        "Withdraw : " + withdrawAmount + "\n" +
                        "Balance : " + customer.getBalance());
                return new Transaction(Transaction.generateRefId(), "DEBIT", customer.getAccountNumber(), (double) withdrawAmount, timestamp);
            } else {
                System.out.println("Insufficient balance $" + customer.getBalance());
            }
        }
        return null;
    }

    //transfer
    public static List<Transaction> transfer(Scanner scanner, List<Customer> customers, Customer customer) {
        System.out.print("" +
                "Please enter destination account and \n" +
                "press enter to continue : ");
        String destAccountNum = scanner.nextLine();

        if (!destAccountNum.matches("[0-9]+")) {
            System.out.println("Invalid account regex");
            return null;
        }

        Customer destCustomer = customers.stream()
                .filter(cust -> Objects.equals(cust.getAccountNumber(), destAccountNum))
                .findFirst()
                .orElse(null);

        if (destCustomer == null) {
            System.out.println("Invalid account");
            return null;
        }

        System.out.print("Please enter transfer amount and press enter to continue : ");
        int transferAmount = 0;
        try {
            transferAmount = scanner.nextInt();
        } catch (Exception exception) {
            System.out.println("Transfer amount is not number");
            scanner.nextLine();
        }
        if (transferAmount > 1000) {
            System.out.println("Maximum amount to transfer is $1000");
        }
        if (transferAmount < 2) {
            System.out.println("Minimum amount to transfer is $1");
        }

        //Generate reference number
        int refId = Transaction.generateRefId();
        System.out.print("\nTransfer Confirmation\n" +
                "Destination Account : " + destAccountNum + " \n" +
                "Transfer Amount     : $" + transferAmount + "\n" +
                "Reference Number    : " + String.format("%06d", refId) + "\n" +
                "\n" +
                "1. Confirm Trx\n" +
                "2. Cancel Trx\n" +
                "Choose option[2]: ");
        int transferOption = 0;
        try {
            transferOption = scanner.nextInt();
        } catch (Exception exception) {
            System.out.println("Input must be numbers");
            scanner.nextLine();
        }

        switch (transferOption) {
            case 1 -> {
                if (customer.getBalance() >= transferAmount) {
                    LocalDateTime timestamp = LocalDateTime.now();
                    customer.setBalance(customer.getBalance() - transferAmount);
                    Transaction outgoing = new Transaction(refId, "DEBIT", customer.getAccountNumber(), (double) transferAmount, timestamp);
                    destCustomer.setBalance(destCustomer.getBalance() + transferAmount);
                    Transaction incoming = new Transaction(refId, "CREDIT", destCustomer.getAccountNumber(), (double) transferAmount, timestamp);
                    System.out.println("\nFund Transfer Summary\n" +
                            "Destination Account : " + destCustomer.getAccountNumber() + "\n" +
                            "Transfer Amount     : $" + transferAmount + "\n" +
                            "Reference Number    : " + String.format("%06d", refId) + "\n" +
                            "Balance             : $" + customer.getBalance() + "\n" +
                            "\n");

                    return List.of(new Transaction[]{outgoing, incoming});
                } else {
                    System.out.println("Insufficient balance $" + customer.getBalance());
                }
            }
        }
        return null;
    }

    //list 10 last transaction
    public static void transactionHistory(Customer customer, List<Transaction> transactions) {
        List<Transaction> customerTransaction = transactions.stream()
                .filter(transaction -> transaction.getTransactionCreator().equals(customer.getAccountNumber()))
                .limit(10)
                .toList();

        TableHelper table  = new TableHelper();
        table.setShowVerticalLines(true);
        table.setHeaders("Reference Number", "Transaction Type", "Account Number", "Amount", "Timestamp");
        for (Transaction transaction: customerTransaction) {
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
