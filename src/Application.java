import helper.InputValidationHelper;
import model.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static controller.ApplicationController.welcomeScreen;


class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John Doe", "012108", 100., "112233"));
        customers.add(new Customer("Jane Doe", "932012", 30., "112244"));
        while (true) {
            welcomeScreen(scanner, customers);
            break;
        }
    }
}