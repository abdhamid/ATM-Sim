package service;

import dao.CustomerDao;
import model.Customer;

import java.util.List;
import java.util.Objects;

import static config.FileConfiguration.CUSTOMER_PATH;

public class CustomerService {
    private final CustomerDao customerDao;
    private final List<Customer> customers;

    public CustomerService() {
        this.customerDao = new CustomerDao();
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
}
