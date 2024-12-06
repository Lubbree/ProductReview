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
        Collections.reverse(customers);
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
