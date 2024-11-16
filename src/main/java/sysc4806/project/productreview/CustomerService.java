package sysc4806.project.productreview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // register new customer with hashed pass
    public Customer registerCustomer(String username, String password, String name) {
        if (customerRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(passwordEncoder.encode(password)); //hahs password
        customer.setName(name);

        return customerRepository.save(customer);
    }

    // check login info
    public boolean authenticateCustomer(String username, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return passwordEncoder.matches(password, customer.getPassword());
        }

        return false; //username not found or password wrong
    }

}
