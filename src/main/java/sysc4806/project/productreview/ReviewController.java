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

    @GetMapping("/myReviews/{id}")
    public String showOtherReviews(@PathVariable long id, Model model) {
        Optional<Customer> rawCustomer = customerRepository.findById(id);
        Customer customer = rawCustomer.get();
        model.addAttribute("customer", customer);
        List<Review> reviews = reviewRepository.findByReviewer(customer);
        model.addAttribute("reviews", reviews);

        return "myReviews";
    }

    @GetMapping("/myReviews/user")
    public String showUserReviews(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("customer", customer);
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
        model.addAttribute("loggedInUser", current);
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


    @GetMapping("/home/follow")
    public String follow(@RequestParam("id") Long id, HttpSession session, Model model) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        Optional<Customer> rawCustomer = customerRepository.findById(id);
        Customer followedUser = rawCustomer.get();

        if(!loggedInUser.getFollowing().contains(followedUser)) {
            loggedInUser.addFollowing(followedUser);
        }

        model.addAttribute("loggedInUser", loggedInUser);
        return "redirect:/home";
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
}
