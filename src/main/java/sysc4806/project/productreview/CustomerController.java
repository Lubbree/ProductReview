package sysc4806.project.productreview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody RegistrationRequest registrationRequest) {
        try {
            Customer newCustomer = customerService.registerCustomer(
                    registrationRequest.getName(),
                    registrationRequest.getEmail(),
                    registrationRequest.getPassword()
            );
            return ResponseEntity.ok(newCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("home/createAccount")
    public String createAccount(Model model) {
        return "createAccount";
    }

    @PostMapping("/home/createAccount")
    public String handleCreateAccount(@RequestParam String name,
                                      @RequestParam String email,
                                      @RequestParam String password,
                                      Model model) {
        // Simulate saving the account data
        // You can add logic here to save the data to a database or process it as needed
        System.out.println("New account created:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        return "redirect:/home";
    }

    @GetMapping("/accountInfo")
    public String viewAccountInfo(@RequestParam("email") String email, Model model) {
        model.addAttribute("account", customerRepository.findByEmail(email));
        return "accountInfo";
    }

    /**
    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest loginRequest) {
        try {
            Customer customer = customerService.loginCustomer(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    **/
}
