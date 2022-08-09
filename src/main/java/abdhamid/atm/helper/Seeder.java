package abdhamid.atm.helper;

import abdhamid.atm.dao.CustomerDao;
import abdhamid.atm.model.Customer;
import abdhamid.atm.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static abdhamid.atm.config.FileConfiguration.CUSTOMER_PATH;

@Configuration
public class Seeder {
    private final CustomerService customerService;
    private final CustomerDao customerDao;

    public Seeder(CustomerService customerService) {
        this.customerService = customerService;
        this.customerDao = new CustomerDao();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            customerSeeder();
        };
    }

    private void customerSeeder() {
        List<Customer> customers = customerDao.readCustomerCSV(CUSTOMER_PATH);
        for (Customer customer: customers) {
            customerService.save(customer);
        }
    }
}