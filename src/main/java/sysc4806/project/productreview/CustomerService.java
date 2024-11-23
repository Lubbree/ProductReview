package sysc4806.project.productreview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

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

        String hashedPassword = hashPassword(password);

        Customer customer = new Customer();
        customer.createAccount(name, email, hashedPassword);
        return customerRepository.save(customer);
    }

    public Customer loginCustomer(String email, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

        if (optionalCustomer.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        Customer customer = optionalCustomer.get();

        if (!verifyPassword(password, customer.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return customer;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean verifyPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
