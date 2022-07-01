import model.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John Doe", "012108", 100., "112233" ));
        customers.add(new Customer("Jane Doe", "932012", 30., "112244"));

        Customer customer = new Customer();
        boolean loggedIn = false;
        boolean exit = false;
        String accountNumber;
        String accountPIN;
        while(!loggedIn){
            System.out.print("Enter Account Number    : ");
            accountNumber = scanner.nextLine();
            System.out.print("Enter PIN   : ");
            accountPIN = scanner.nextLine();

            if (accountNumber.length() != 6){
                System.out.println("Account Number should have 6 digits length");
            }
            if (accountNumber.contains("[a-zA-Z]+")){
                System.out.println("Account Number should only contains numbers");
            }
            if (accountPIN.length() != 6) {
                System.out.println("PIN should have 6 digits length");
            }
            if (accountPIN.contains("[a-zA-Z]+")){
                System.out.println("PIN should only contains numbers");
            }
            String finalAccountPIN = accountPIN;
            String finalAccountNumber = accountNumber;
            customer = customers.stream()
                    .filter(cust -> Objects.equals(cust.getAccountNumber(), finalAccountNumber))
                    .filter(cust -> Objects.equals(cust.getPin(), finalAccountPIN))
                    .findFirst()
                    .orElse(null);

            if (customer != null){
                System.out.println("Login successful");
                loggedIn = true;
            } else {
                System.out.println("Invalid Account Number/PIN");
            }
        }
        while (!exit) {
            System.out.print("""

                    1. Withdraw
                    2. Fund Transfer
                    3. Exit
                    Please choose option[3]:\040""");
            int option = 0;

            try {
                option = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("Input must be numbers");
                scanner.nextLine();
            }

            switch (option) {
                case 1 -> {
                    System.out.print("""

                            1. $10
                            2. $50
                            3. $100
                            4. Other
                            5. Back
                            Please choose option[5]:\040""");
                    int withdrawOption = scanner.nextInt();
                    int withdrawAmount = 0;
                    switch (withdrawOption) {
                        case 1 -> withdrawAmount = 10;
                        case 2 -> withdrawAmount = 50;
                        case 3 -> withdrawAmount = 100;
                        case 4 -> {
                            System.out.println("Other Withdraw\n" +
                                    "Enter amount to withdraw: ");
                            try {
                                withdrawAmount = scanner.nextInt();
                            } catch (Exception exception) {
                                System.out.println("Invalid number");
                                scanner.nextLine();
                            }
                        }
                        case 5 -> {
                            break;
                        }
                        default -> System.out.println("Unknown command");
                    }
                    if (withdrawAmount > 0) {
                        if (withdrawAmount % 10 != 0) System.out.println("Invalid amount");
                        else if (withdrawAmount > 1000) System.out.println("Maximum amount to withdraw is $1000");
                        else if (customer.getBalance() >= withdrawAmount) {
                            customer.setBalance(customer.getBalance() - (double) withdrawAmount);
                            System.out.println("\nSummary\n" +
                                    "Date : " + LocalDateTime.now().format(dtf) + "\n" +
                                    "Withdraw : " + withdrawAmount + "\n" +
                                    "Balance : " + customer.getBalance());
                        } else {
                            System.out.println("Insufficient balance $" + customer.getBalance());
                        }
                    } else {
                        System.out.println("Invalid amount");
                    }
                }
                case 2 -> {
                    System.out.print("" +
                            "Please enter destination account and \n" +
                            "press enter to continue : ");
                    scanner.nextLine();
                    String destAccount = scanner.nextLine();
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
                        break;
                    }
                    if (transferAmount < 2) {
                        System.out.println("Minimum amount to transfer is $1");
                        break;
                    }
                    //Generate reference number
                    Random rnd = new Random();
                    int refNumber = rnd.nextInt(999999);
                    System.out.print("\nTransfer Confirmation\n" +
                            "Destination Account : " + destAccount + " \n" +
                            "Transfer Amount     : $" + transferAmount + "\n" +
                            "Reference Number    : " + String.format("%06d", refNumber) + "\n" +
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
                    Customer destCustomer;
                    switch (transferOption) {
                        case 1 -> {
                            destCustomer = customers.stream()
                                    .filter(cust -> cust.getAccountNumber().equals(destAccount))
                                    .findFirst()
                                    .orElse(null);

                            if (destCustomer != null) {

                                if (customer.getBalance() >= transferAmount) {
                                    customer.setBalance(customer.getBalance() - transferAmount);
                                    destCustomer.setBalance(destCustomer.getBalance() + transferAmount);
                                    System.out.println("\nFund Transfer Summary\n" +
                                            "Destination Account : " + destCustomer.getAccountNumber() + "\n" +
                                            "Transfer Amount     : $" + transferAmount + "\n" +
                                            "Reference Number    : " + String.format("%06d", refNumber) + "\n" +
                                            "Balance             : $" + customer.getBalance() + "\n" +
                                            "\n" +
                                            "1. Transaction\n" +
                                            "2. Exit\n" +
                                            "Choose option[2]: ");
                                    int summaryOption = scanner.nextInt();
                                    switch (summaryOption) {
                                        case 1 -> {
                                            break;
                                        }
                                        case 2 -> exit = true;
                                    }
                                } else {
                                    System.out.println("Insufficient balance $" + customer.getBalance());
                                }
                            } else {
                                System.out.println("Invalid account");
                            }
                        }
//                        case 2 -> {
//                            break;
//                        }
                    }
                }
                case 3 -> exit = true;
            }
        }
    }
}