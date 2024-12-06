package sysc4806.project.productreview;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;

    public ReviewController(ReviewRepository reviewRepository, CustomerRepository customerRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myReviews")
    public String showReviews(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        List<Review> reviews = reviewRepository.findByReviewer(customer);
        model.addAttribute("reviews", reviews);
        return "myReviews";
    }

    @GetMapping("/newReview")
    public String reviewForm(Model model, @RequestParam(name = "product") Optional<Product> product) {
        model.addAttribute("product", product);
        model.addAttribute("Review", new Review());
        return "newReview";
    }

    @PostMapping("/newReview")
    public String processNewReview(@ModelAttribute Review review, HttpSession session, Model model) {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        review.setReviewer(customer);
        review.setReviewDate(now);
        Product product = ((Product) session.getAttribute("currentProduct"));
        review.setProduct(product);
        reviewRepository.save(review);
        session.removeAttribute("currentProduct");
        return "redirect:/home/product/" + product.getId();
    }

    @GetMapping("/users")
    public String showUsers(Model model, HttpSession session) {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        Customer current = (Customer) session.getAttribute("loggedInUser");
        customers.remove(current);

        model.addAttribute("customers", customers);
        return "users";
    }

    @GetMapping("/users/jaccard")
    public String showJaccardUsers(Model model, HttpSession session) {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        Customer current = (Customer) session.getAttribute("loggedInUser");
        customers.remove(current);
        for (Customer customer : customers) {
            customer.setJaccard_Index(jaccardDistance(current, customer));
        }

        customers.sort(new JaccardComparator());
        Collections.reverse(customers);
        model.addAttribute("customers", customers);
        return "users";
    }

    @GetMapping("/users/popular")
    public String showPopularUsers(Model model, HttpSession session) {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        Customer current = (Customer) session.getAttribute("loggedInUser");
        customers.remove(current);

        customers.sort(new FollowerComparator());
        Collections.reverse(customers);
        model.addAttribute("customers", customers);
        return "users";
    }

    public double jaccardDistance(Customer c1, Customer c2) {
        List<Review> c1Reviews = new ArrayList<>(c1.getReviews());
        List<Review> c2Reviews = new ArrayList<>(c2.getReviews());

        sortReviews(c1Reviews);
        sortReviews(c2Reviews);
        List<Review> temp = c1Reviews;

        c1Reviews.retainAll(c2Reviews);
        temp.addAll(c2Reviews);

        int intersection = c1Reviews.size();
        int union = temp.size();

        return ((double) intersection / union);
    }

    public static void sortReviews(List<Review> review){
        review.sort(Comparator.comparing(Review::getId));
    }

    @GetMapping("/users/{id}/profile")
    public String viewUserProfile(@PathVariable Long id, Model model) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error";
        }

        Customer customer = customerOptional.get();
        List<Review> reviews = reviewRepository.findByReviewer(customer);

        model.addAttribute("user", customer);
        model.addAttribute("reviews", reviews);

        return "userProfile";
    }

}
