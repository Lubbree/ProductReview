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
        Map<Long, Integer> distanceMap = distance(current);

        model.addAttribute("following", current.getFollowing());
        model.addAttribute("customers", customers);
        model.addAttribute("loggedInUser", current);
        model.addAttribute("distanceMap", distanceMap);
        return "users";
    }

    @GetMapping("/users/jaccard")
    public String showJaccardUsers(Model model, HttpSession session) {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        Customer current = (Customer) session.getAttribute("loggedInUser");
        Map<Long, Integer> distanceMap = distance(current);
        model.addAttribute("following", current.getFollowing());

        for (Customer customer : customers) {
            customer.setJaccard_Index(jaccardDistance(current, customer));
        }

        customers.sort(new JaccardComparator());
        model.addAttribute("customers", customers);
        model.addAttribute("loggedInUser", current);
        model.addAttribute("distanceMap", distanceMap);
        return "users";
    }

    @GetMapping("/users/popular")
    public String showPopularUsers(Model model, HttpSession session) {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        Customer current = (Customer) session.getAttribute("loggedInUser");
        Map<Long, Integer> distanceMap = distance(current);
        customers.remove(current);
        model.addAttribute("following", current.getFollowing());

        customers.sort(new FollowerComparator());
        Collections.reverse(customers);
        model.addAttribute("customers", customers);
        model.addAttribute("loggedInUser", current);
        model.addAttribute("distanceMap", distanceMap);
        return "users";
    }

    public static double jaccardDistance(Customer c1, Customer c2) {
        HashSet<Review> s1Reviews = new HashSet<>(c1.getReviews());
        HashSet<Review> s2Reviews = new HashSet<>(c2.getReviews());

        return 1.0 - jaccard_index(s1Reviews, s2Reviews);
    }

    static HashSet<Review> intersection(HashSet<Review> a, HashSet<Review> b) {
        HashSet<Review> intersect = new HashSet<>();
        for (Review n : a) {
            for (Review r : b){
                if (r.getStarRating() == n.getStarRating()){
                    if (Objects.equals(r.getProduct().getId(), n.getProduct().getId())){
                        intersect.add(n);
                    }
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
        double jaccard_in  = (float) size_in / (float)(size_s1 + size_s2 - size_in);
        return jaccard_in;
    }

    public static Map<Long, Integer> distance(Customer customer) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(customer.getUserId(), 0);
        List<Customer> currentRow = new ArrayList<>(customer.getFollowing());
        int distance = 1;
        while (!currentRow.isEmpty()) {
            List<Customer> nextRow = new ArrayList<>();
            for (Customer c: currentRow) {
                if (!map.containsKey(c.getUserId())) {
                    map.put(c.getUserId(), distance);
                    nextRow.add(c);
                }
            }
            currentRow = new ArrayList<>();
            for (Customer c: nextRow) {
                for (Customer following: c.getFollowing()) {
                    if (!map.containsKey(following.getUserId())) {
                        currentRow.add(following);
                    }
                }
            }
            distance += 1;
        }

        return map;
    }

    public static void sortReviews(List<Review> review){
        review.sort(Comparator.comparing(Review::getId));
    }
}
