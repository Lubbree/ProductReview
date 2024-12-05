package sysc4806.project.productreview;

import jakarta.servlet.http.HttpSession;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;


@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductController(ProductRepository productRepository,
                             ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        model.addAttribute("products", productRepository.findAll());

        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        session.removeAttribute("currentProduct");

        return "home";
    }

    @GetMapping("/home/product/{id}")
    public String viewProduct(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Product> rawProduct = productRepository.findById(id);
        Product product = rawProduct.get();
        session.setAttribute("currentProduct", product);

        double avg = 0;
        for (Review review : product.getReviews()) {
            avg += review.getStarRating();
        }
        DecimalFormat numberFormat = new DecimalFormat("#.#");
        model.addAttribute("stars", numberFormat.format(avg/product.getReviews().size()));
        model.addAttribute("product", product);
        List<Review> reviews = reviewRepository.findByProduct(product);
        model.addAttribute("reviews", reviews);
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
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

    @GetMapping("/home/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear the session
        return "redirect:/home";
    }

    @GetMapping("/accountInfo")
    public String accountInfo(Model model, HttpSession session) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("customerInfo", loggedInUser);
        return "accountInfo";
    }

//    @PostConstruct
//    public void init(){
//        Product product1 = new Product();
//        product1.setName("JetStream® 3 Piece Hardside Luggage Set");
//        product1.setCategory("Furniture/Luggage");
//        product1.setDescription("https://www.walmart.ca/en/ip/jetstream-3-piece-hardside-luggage-set-cream/6000206846841");
//        productRepository.save(product1);
//
//        Product product2 = new Product();
//        product2.setName("AirPods Pro (2nd generation) with USB-C");
//        product2.setCategory("Technology");
//        product2.setDescription("https://www.walmart.ca/en/ip/AirPods-Pro-2nd-generation-with-USB-C/6000206604258");
//        productRepository.save(product2);
//
//        Product product3 = new Product();
//        product3.setName("Pokémon 24'' Plush - Snorlax");
//        product3.setCategory("Toys");
//        product3.setDescription("https://www.walmart.ca/en/ip/pokmon-24-plush-snorlax-bluewhite/6000208095332");
//        productRepository.save(product3);
//    }
}
