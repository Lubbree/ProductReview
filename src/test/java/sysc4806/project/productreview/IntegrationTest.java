package sysc4806.project.productreview;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTest {

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16.4"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }


    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @BeforeEach
    void setUp() {
        customerRepo.deleteAll();
        productRepo.deleteAll();
        reviewRepo.deleteAll();
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
    }

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

    @Transactional
    @Test
    public void testAddAndRetrieveReviews() {
        String pName = "Test";
        String pCategory = "111";
        String pDescription = "Test product";

        Product p = new Product();
        p.setName(pName);
        p.setCategory(pCategory);
        p.setDescription(pDescription);

        Review r = new Review();
        String reviewText = "awesome";
        r.setReviewText(reviewText);

        p.addReview(r);

        Product saved = productRepo.save(p);
        Product foundProduct = productRepo.findById(saved.getId()).orElse(null);

        assertNotNull(foundProduct);
        List<Review> reviews = foundProduct.getReviews();
        assertEquals(1, reviews.size());
        assertEquals(reviewText, reviews.get(0).getReviewText());

    }

    @Test
    public void testSaveAndFindCustomer(){
        String cName = "Sam";
        String cEmail = "sam@gmail.ca";
        String cPassword = "password";

        Customer c = new Customer();
        c.setName(cName);
        c.setEmail(cEmail);
        c.setPassword(cPassword);

        Customer saved = customerRepo.save(c);
        Customer foundCustomer = customerRepo.findById(saved.getUserId()).orElse(null);

        assertNotNull(foundCustomer);
        assertEquals(cName, foundCustomer.getName());
        assertEquals(cEmail, foundCustomer.getEmail());
        assertEquals(cPassword, foundCustomer.getPassword());

    }

    @Transactional
    @Test
    public void testReviewCustomerAndProduct() {
        String pName = "Test";
        String pCategory = "111";
        String pDescription = "Test product";

        Product p = new Product();
        p.setName(pName);
        p.setCategory(pCategory);
        p.setDescription(pDescription);

        String cName = "Sam";
        String cEmail = "sam@gmail.ca";
        String cPassword = "password";

        Customer c = new Customer();
        c.setName(cName);
        c.setEmail(cEmail);
        c.setPassword(cPassword);

        Review review = new Review();
        String reviewText = "Great product!";
        review.setReviewText(reviewText);
        review.setStarRating(5);
        reviewRepo.save(review);

        p.addReview(review);
        productRepo.save(p);

        c.addReview(review);
        customerRepo.save(c);

        Customer foundCustomer = customerRepo.findById(c.getUserId()).get();
        assertEquals(1, foundCustomer.getReviews().size());
        assertEquals(reviewText, foundCustomer.getReviews().iterator().next().getReviewText());
        assertEquals(pName, foundCustomer.getReviews().iterator().next().getProduct().getName());
    }
}

