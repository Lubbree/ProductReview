package sysc4806.project.productreview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class IntegrationTest {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Test
    public void testSaveAndFindProduct() {
        Product p = new Product();
        p.setName("Test");
        p.setCategory("111");
        p.setDescription("Test product");

        Product saved = productRepo.save(p);
        Product foundProduct = productRepo.findById(saved.getId()).orElse(null);

        assertNotNull(foundProduct);
        assertEquals("Test", foundProduct.getName());
        assertEquals("111", foundProduct.getCategory());
        assertEquals("Test product", foundProduct.getDescription());
    }

    @Test
    public void testAddAndRetrieveReviews() {
        Product p = new Product();
        p.setName("Test");
        p.setCategory("111");
        p.setDescription("Test product");

        Review r = new Review();
        p.addReview(r);
        r.setReviewText("awesome");

        Product saved = productRepo.save(p);
        Product foundProduct = productRepo.findById(saved.getId()).orElse(null);

        assertNotNull(foundProduct);
        List<Review> reviews = foundProduct.getReviews();
        assertEquals(1, reviews.size());
        assertEquals("awesome", reviews.get(0).getReviewText());

    }

    @Test
    public void testSaveAndFindCustomer(){
        Customer c = new Customer();
        c.setName("Sam");
        c.setUsername("sam123");
        c.setPassword("password123");
        Customer saved = customerRepo.save(c);
        //fix this
        Customer foundCustomer = customerRepo.findById(saved.getUserId()).orElse(null);

        assertNotNull(foundCustomer);
        assertEquals("Sam", foundCustomer.getName());

    }

    @Test
    public void testReviewCustomerAndProduct() {
        Product p = new Product();
        p.setName("Test");
        p.setCategory("111");
        p.setDescription("Test product");

        Customer customer = new Customer();
        customer.setName("Sam");
        customer.setUsername("sam123"); // Set username (required field)
        customer.setPassword("password123"); // Set password (required field)

        Review review = new Review();
        review.setReviewText("Great product!");
        review.setStarRating(5);

        p.addReview(review); // Associate the review with the product
        customer.addReview(review); // Associate the review with the customer

        customerRepo.save(customer); // Save the customer, ensuring all required fields are set

        Customer foundCustomer = customerRepo.findById(customer.getUserId()).orElse(null);

        assertEquals(1, foundCustomer.getReviews().size());
        assertEquals("Great product!", foundCustomer.getReviews().iterator().next().getReviewText());
        assertEquals("Test", foundCustomer.getReviews().iterator().next().getProduct().getName());
    }
}

