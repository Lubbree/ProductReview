package sysc4806.project.productreview;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerTest {
    private Customer customer;
    private Customer testCustomer1;
    private Customer testCustomer2;
    private Review testReview1;
    private Review testReview2;

    @Before
    public void setUp() {
        customer = new Customer();
        testCustomer1 = new Customer();
        testCustomer2 = new Customer();
        testReview1 = new Review();
        testReview2 = new Review();
    }

    @Test
    public void testInit(){
        assertNull(customer.getName());
        assertNull(customer.getUserId());
        assertEquals(new HashSet<Review>(), customer.getReviews());
        assertEquals(new HashSet<Customer>(), customer.getFollowing());
    }

    @Test
    public void testSetters(){
        Set<Customer> testFollowing = new HashSet<>();
        testFollowing.add(testCustomer1);
        testFollowing.add(testCustomer2);
        Set<Review> testReviews = new HashSet<>();
        testReviews.add(testReview1);
        testReviews.add(testReview2);

        customer.setName("John Smith");
        assertEquals("John Smith", customer.getName());

        customer.setUserId("0015");
        assertEquals("0015", customer.getUserId());

        customer.setReviews(testReviews);
        assertEquals(testReviews, customer.getReviews());

        customer.setFollowing(testFollowing);
        assertEquals(testFollowing, customer.getFollowing());
    }

    @Test
    public void testAddFollowing(){
        Set<Customer> testFollowing = new HashSet<>();
        assertEquals(testFollowing, customer.getFollowing());

        customer.addFollowing(testCustomer1);
        testFollowing.add(testCustomer1);
        assertEquals(testFollowing, customer.getFollowing());

        customer.addFollowing(testCustomer2);
        assertNotEquals(testFollowing, customer.getFollowing());

        testFollowing.add(testCustomer2);
        assertEquals(testFollowing, customer.getFollowing());
    }

    @Test
    public void testRemoveFollowing(){
        Set<Customer> testFollowing = new HashSet<>();
        customer.addFollowing(testCustomer1);
        customer.addFollowing(testCustomer2);
        testFollowing.add(testCustomer1);
        testFollowing.add(testCustomer2);
        assertEquals(testFollowing, customer.getFollowing());

        customer.removeFollowing(testCustomer1);
        assertNotEquals(testFollowing, customer.getFollowing());

        testFollowing.remove(testCustomer1);
        assertEquals(testFollowing, customer.getFollowing());

        customer.removeFollowing(testCustomer2);
        assertEquals(new HashSet<Customer>(), customer.getFollowing());
    }

    @Test
    public void testAddReview(){
        customer.addReview(testReview1);
        List<Review> testReviews = new ArrayList<>(customer.getReviews());
        assertEquals(testReview1, testReviews.get(0));

        customer.addReview(testReview2);
        testReviews.add(testReview2);
        assertEquals(testReview2, testReviews.get(1));
    }

    @Test
    public void testRemoveReview(){
        customer.addReview(testReview1);
        customer.addReview(testReview2);

        customer.removeReview(testReview1);
        List<Review> testReviews = new ArrayList<>(customer.getReviews());
        assertEquals(testReview2, testReviews.get(0));

        customer.removeReview(testReview2);
        testReviews = new ArrayList<>(customer.getReviews());
        assertEquals(new ArrayList<>(), testReviews);
    }
}
