package sysc4806.project.productreview;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Testcontainers
@DataJpaTest
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
        //postgres.start();
    }

    @AfterAll
    static void afterAll() {
        //postgres.stop();
    }


    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @BeforeEach
    void setUp() {
        //RestAssured.baseURI = "http://localhost:" + port;
        //customerRepo.deleteAll();
        //productRepo.deleteAll();
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

        Customer saved = customerRepo.save(c);
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

        Review review = new Review();
        review.setReviewText("Great product!");
        review.setStarRating(5);

        p.addReview(review);

        customer.addReview(review);
        customerRepo.save(customer);

        Customer foundCustomer = customerRepo.findById(customer.getUserId()).get();
        assertEquals(1, foundCustomer.getReviews().size());
        assertEquals("Great product!", foundCustomer.getReviews().iterator().next().getReviewText());
        assertEquals("Test", foundCustomer.getReviews().iterator().next().getProduct().getName());
    }
}

