package sysc4806.project.productreview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            Customer customer = customerService.loginCustomer(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            session.setAttribute("loggedInUser", customer);
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); // Clear the session
        return ResponseEntity.ok("Logged out successfully");
    }


}
