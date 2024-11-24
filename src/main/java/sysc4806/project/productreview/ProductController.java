package sysc4806.project.productreview;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        model.addAttribute("products", productRepository.findAll());

        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        return "home";
    }

    @GetMapping("/home/product/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id));
        return "product";
    }

    @GetMapping("home/createAccount")
    public String createAccount(Model model) {
        return "createAccount";
    }

    @GetMapping("home/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/home/login")
    public String handleLogin(@RequestParam String email, @RequestParam String password, Model model) {
        // Simulate saving the account data
        // You can add logic here to save the data to a database or process it as needed
        System.out.println("Login info");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        return "redirect:/home";

    }


}
