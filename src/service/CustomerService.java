package service;

import model.Customer;

import java.util.List;
import java.util.Objects;

public class CustomerService {
    private final List<Customer> customers;

    public CustomerService(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getAll() {
        return this.customers;
    }

    public Customer setCurrentCustomer(String accountNumber, String customerPIN) {
        return customers.stream()
                .filter(cust -> Objects.equals(cust.getAccountNumber(), accountNumber))
                .filter(cust -> Objects.equals(cust.getPin(), customerPIN))
                .findFirst()
                .orElse(null);
    }

    public Customer getByAccNumber(String accountNumber) {
        return customers.stream()
                .filter(cust -> Objects.equals(cust.getAccountNumber(), accountNumber))
                .findFirst()
                .orElse(null);
    }

    public Customer getDestinationAccount(Customer customer, String destinationAccountNumber) {
        if (customer.getAccountNumber().equals(destinationAccountNumber)) {
            System.out.println("Can't transfer to your own account");
            return null;
        } else return getByAccNumber(destinationAccountNumber);
    }
}
