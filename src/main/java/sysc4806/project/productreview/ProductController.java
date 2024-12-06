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
    private final CustomerRepository customerRepository;

    public ProductController(ProductRepository productRepository,
                             ReviewRepository reviewRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        List<Product> products = (List<Product>) productRepository.findAll();
        for (Product product : products) {
            double avg = 0;
            for (Review review : product.getReviews()) {
                avg += review.getStarRating();
            }
            DecimalFormat numberFormat = new DecimalFormat("#.#");
            product.setStars(numberFormat.format(avg/product.getReviews().size()));
        }

        model.addAttribute("products", products);
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
        product.setStars(numberFormat.format(avg/product.getReviews().size()));
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
        product.setStars(numberFormat.format(avg/product.getReviews().size()));
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

    @GetMapping("/home/follow")
    public String follow(@RequestParam("id") Long id, HttpSession session, Model model) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        Optional<Customer> rawCustomer = customerRepository.findById(id);
        Customer followedUser = rawCustomer.get();
        loggedInUser.addFollowing(followedUser);
        if(loggedInUser.isFollowing(followedUser)) {
            followedUser.setFollower_Count(followedUser.getFollower_Count() + 1);
        }
        customerRepository.save(followedUser);

        model.addAttribute("loggedInUser", loggedInUser);
        return "redirect:/home";
    }


    @GetMapping("/home/unfollow")
    public String unfollow(@RequestParam("id") Long id, HttpSession session, Model model){
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        Optional<Customer> rawCustomer = customerRepository.findById(id);
        Customer followedUser = rawCustomer.get();
        loggedInUser.removeFollowing(followedUser);

        customerRepository.save(loggedInUser);
        customerRepository.save(followedUser);

        session.setAttribute("loggedInUser", customerRepository.findById(loggedInUser.getUserId()).orElse(null));
        return "redirect:/home";
    }

    @GetMapping("/home/rating")
    public String homeRating(Model model, HttpSession session) {
        List<Product> products = (List<Product>) productRepository.findAll();
        for (Product product : products) {
            double avg = 0;
            for (Review review : product.getReviews()) {
                avg += review.getStarRating();
            }
            DecimalFormat numberFormat = new DecimalFormat("#.#");
            product.setStars(numberFormat.format(avg / product.getReviews().size()));
        }
        products.sort((p1, p2) -> {
            double s1 = Double.parseDouble(p1.getStars()) * 100;
            double s2 = Double.parseDouble(p2.getStars()) * 100;
            return (int) (s1 - s2);
        });
        Collections.reverse(products);
        model.addAttribute("products", products);
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        session.removeAttribute("currentProduct");

        return "home";
    }

    @GetMapping("/home/userrating")
    public String homeUserRating(Model model, HttpSession session) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        List<Product> products = (List<Product>) productRepository.findAll();
        for (Product product : products) {
            double avg = 0;
            int numReviews = 0;
            for (Review review : product.getReviews()) {
                if (loggedInUser.isFollowing(review.getReviewer())) {
                    avg += review.getStarRating();
                    numReviews++;
                }
            }
            if (avg != 0) {
                DecimalFormat numberFormat = new DecimalFormat("#.#");
                product.setStars(numberFormat.format(avg / numReviews));
            } else {
                product.setStars("0");
            }
        }

        products.sort((p1, p2) -> {
            double s1 = Double.parseDouble(p1.getStars()) * 100;
            double s2 = Double.parseDouble(p2.getStars()) * 100;
            return (int) (s1 - s2);
        });
        Collections.reverse(products);
        model.addAttribute("products", products);

        session.removeAttribute("currentProduct");

        return "home";
    }
}
