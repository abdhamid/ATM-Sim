package abdhamid.atm.service;

import abdhamid.atm.model.Customer;
import abdhamid.atm.repository.CustomerRepository;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findByAccountNumber(String accNumber) {
        return customerRepository.findCustomerByAccountNumber(accNumber);
    }
}
