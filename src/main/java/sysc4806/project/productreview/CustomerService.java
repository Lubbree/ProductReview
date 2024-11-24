package sysc4806.project.productreview;

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
}
