package abdhamid.atm.service;

import abdhamid.atm.model.Customer;
import abdhamid.atm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final CustomerRepository customerRepository;

    public AccountService(CustomerRepository customerRepository) {
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

    public Customer getByAccountNumber(String destinationAccount) {
        return customerRepository.findCustomerByAccountNumber(destinationAccount).orElseThrow(() -> new EntityNotFoundException("Invalid account"));
    }
}
