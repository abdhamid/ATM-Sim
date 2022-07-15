import model.Customer;
import model.Transaction;
import service.ApplicationService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static dao.CustomerDao.readCustomerCSV;
import static dao.TransactionDao.readTransactionCSV;
import static dao.TransactionDao.writeTransactionCSV;
import static service.ApplicationService.transactionHistory;


public class Driver {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        final String CUSTOMER_PATH = "D:\\Work\\Training\\Java\\ATM-Sim\\db.csv";
        final String TRANSACTION_PATH = "D:\\Work\\Training\\Java\\ATM-Sim\\transaction.csv";
        List<Customer> customers = readCustomerCSV(CUSTOMER_PATH);
        List<Transaction> transactions = readTransactionCSV(TRANSACTION_PATH);
        System.out.println(customers);
        Customer currentCust = null;
        boolean loggedIn = false;
        boolean exit = false;
        while (!loggedIn) {
            currentCust = ApplicationService.login(scanner, customers, currentCust);
            loggedIn = true;
        }

        while (!exit) {
            System.out.print("""
                    
                    1. Withdraw
                    2. Fund Transfer
                    3. Transaction History
                    4. Exit
                    Please choose option[3]:\040""");
            int optionNum = 0;
            String mainOption = scanner.nextLine();

            if (!mainOption.matches("[1-4]")){
                System.out.println("Invalid command");
            } else if (mainOption.equals("")) {
                optionNum = 0;
            } else {
                optionNum = Integer.parseInt(mainOption);
            }

            switch (optionNum) {
                case 1 -> {
                    Transaction transaction = ApplicationService.withdraw(scanner, currentCust);
                    System.out.println(transaction);
                    if (transaction != null) {
                        transactions.add(transaction);
                        writeTransactionCSV(transactions, TRANSACTION_PATH);
                    }
                }
                case 2-> {
                    List<Transaction> transfer = ApplicationService.transfer(scanner, customers, currentCust);
                    if (transfer != null) {
                        transactions.add(transfer.get(0));
                        transactions.add(transfer.get(1));
                        writeTransactionCSV(transactions, TRANSACTION_PATH);
                    }
                }
                case 3 -> {
                    transactionHistory(currentCust, transactions);
                }
                case 4 -> exit=true;
            }

        }
//        System.out.println("hello");
    }

}
