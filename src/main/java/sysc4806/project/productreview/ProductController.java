package sysc4806.project.productreview;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.*;

import static sysc4806.project.productreview.ReviewController.jaccardDistance;


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

    @GetMapping("/home/product/{id}/jaccard")
    public String viewProductJaccard(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Product> rawProduct = productRepository.findById(id);
        Product product = rawProduct.get();
        session.setAttribute("currentProduct", product);
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        double avg = 0;
        for (Review review : product.getReviews()) {
            avg += review.getStarRating();
        }

        DecimalFormat numberFormat = new DecimalFormat("#.#");
        model.addAttribute("stars", numberFormat.format(avg/product.getReviews().size()));
        model.addAttribute("product", product);
        List<Review> reviews = reviewRepository.findByProduct(product);
        for (Review review : reviews) {
            review.setJaccard_index(jaccardDistance(loggedInUser, review.getReviewer()));
        }
        reviews.sort((r1, r2) -> {
            double s1 = r1.getJaccard_index() * 100;
            double s2 = r2.getJaccard_index() * 100;
            return (int) (s1 - s2);
        });
        Collections.reverse(reviews);

        model.addAttribute("reviews", reviews);
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
}
