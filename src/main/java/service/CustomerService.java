package service;

import dao.CustomerDao;
import model.Customer;

import java.util.List;
import java.util.Objects;

import static config.FileConfiguration.CUSTOMER_PATH;
import static dao.CustomerDao.writeCSV;

public class CustomerService {
    private final List<Customer> customers;

    public CustomerService() {
        CustomerDao customerDao = new CustomerDao();
        this.customers = customerDao.readCustomerCSV(CUSTOMER_PATH);
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

    public void addNewCustomer(Customer customer) {
        Customer dups = customers.stream()
                .filter(customer::equals)
                .findAny()
                .orElse(null);

        if (dups==null) System.out.println("duplicate customer");
        else {
            customers.add(customer);
            writeCSV(customers, CUSTOMER_PATH);
        }
    }

    public void updateCustomer(Customer customer) {
        Customer updated = customers.stream()
                .filter(customer::equals)
                .findAny()
                .orElse(null);

        if (updated==null) System.out.println("Can't find customer");
        else {
            customers.add(customer);
            writeCSV(customers, CUSTOMER_PATH);
        }
    }
}
