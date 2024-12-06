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

        model.addAttribute("following", current.getFollowing());
        model.addAttribute("customers", customers);
        model.addAttribute("loggedInUser", current);
        return "users";
    }

    @GetMapping("/users/jaccard")
    public String showJaccardUsers(Model model, HttpSession session) {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        Customer current = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("following", current.getFollowing());

        for (Customer customer : customers) {
            customer.setJaccard_Index(jaccardDistance(current, customer));
        }

        customers.sort(new JaccardComparator());
        model.addAttribute("customers", customers);
        model.addAttribute("loggedInUser", current);
        return "users";
    }

    @GetMapping("/users/popular")
    public String showPopularUsers(Model model, HttpSession session) {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        Customer current = (Customer) session.getAttribute("loggedInUser");
        customers.remove(current);
        model.addAttribute("following", current.getFollowing());

        customers.sort(new FollowerComparator());
        Collections.reverse(customers);
        model.addAttribute("customers", customers);
        model.addAttribute("loggedInUser", current);
        return "users";
    }

    public static double jaccardDistance(Customer c1, Customer c2) {
        HashSet<Review> s1Reviews = new HashSet<>(c1.getReviews());
        HashSet<Review> s2Reviews = new HashSet<>(c2.getReviews());

        System.out.println(1.0 - jaccard_index(s1Reviews, s2Reviews));
        return 1.0 - jaccard_index(s1Reviews, s2Reviews);
    }

    static HashSet<Review> intersection(HashSet<Review> a, HashSet<Review> b) {
        HashSet<Review> intersect = new HashSet<>();
        for (Review n : a) {
            for (Review r : b){
                System.out.println("Star rating of user: " + n.getStarRating());
                System.out.println("Star rating of other: " + r.getStarRating());
                if (r.getStarRating() == n.getStarRating()){
                    if (Objects.equals(r.getProduct().getId(), n.getProduct().getId())){
                        intersect.add(n);
                    }
                    System.out.println("User id: " + n.getProduct().getId());
                    System.out.println("Other id: " + r.getProduct().getId());
                }
            }
        }
        return intersect;
    }

    // Function to return the Jaccard index of two sets
    static double jaccard_index(HashSet<Review> s1, HashSet<Review> s2) {
        int size_s1 = s1.size();
        int size_s2 = s2.size();
        HashSet<Review> intersect = intersection(s1, s2);
        int size_in = intersect.size();

        // Calculate the Jaccard index
        // using the formula
        System.out.println("Intersect size: " + size_in);
        System.out.println("Union size: " + (float)(size_s1 + size_s2 - size_in));
        double jaccard_in  = (float) size_in / (float)(size_s1 + size_s2 - size_in);
        System.out.println("jaccard index" + jaccard_in);
        return jaccard_in;
    }

    public static void sortReviews(List<Review> review){
        review.sort(Comparator.comparing(Review::getId));
    }
}
