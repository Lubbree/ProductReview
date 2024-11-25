package sysc4806.project.productreview;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer registerCustomer(String name, String email, String password) {
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Create and save the new customer
        Customer customer = new Customer();
        customer.createAccount(name, email, password); // Store raw password directly
        return customerRepository.save(customer);
    }


    public Customer loginCustomer(String email, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

        if (optionalCustomer.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        Customer customer = optionalCustomer.get();

        // Directly compare the raw password
        if (!customer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return customer;
    }

    @PostConstruct
    public void init() {
        Customer customer1 = new Customer();
        customer1.createAccount("Jack", "jack1@gmail.com", "123");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.createAccount("Jane", "jane2@gmail.com", "12345");
        customerRepository.save(customer2);

        Customer customer3 = new Customer();
        customer3.createAccount("John", "john3@gmail.com", "abcde");
        customerRepository.save(customer3);
    }
}
