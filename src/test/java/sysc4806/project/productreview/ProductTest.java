package sysc4806.project.productreview;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class ProductTest {
    private Product product;
    private Review testReview1;
    private Review testReview2;

    @BeforeEach
    public void setUp(){
        product = new Product();
        testReview1 = new Review();
        testReview2 = new Review();
    }

    @Test
    public void testInit(){
        assertNull(product.getName());
        assertNull(product.getCategory());
        assertNull(product.getId());
        assertEquals(new ArrayList<>(), product.getReviews());
    }

    @Test
    public void testSetters(){
        product.setName("Wooden Table");
        assertEquals("Wooden Table", product.getName());

        product.setCategory("Furniture");
        assertEquals("Furniture", product.getCategory());

        Long testLong = 1L;
        product.setId(1L);
        assertEquals(testLong, product.getId());

        List<Review> testReviews = new ArrayList<>();
        testReviews.add(testReview1);
        testReviews.add(testReview2);
        product.setReviews(testReviews);
        assertEquals(testReviews, product.getReviews());
    }

    @Test
    public void testAddReview(){
        product.addReview(testReview1);
        assertEquals(testReview1, product.getReviews().get(0));

        product.addReview(testReview2);
        assertEquals(testReview2, product.getReviews().get(1));
    }

    @Test
    public void testRemoveReview(){
        product.addReview(testReview1);
        assertEquals(testReview1, product.getReviews().get(0));

        product.addReview(testReview2);
        assertEquals(testReview2, product.getReviews().get(1));

        product.removeReview(testReview1);
        assertEquals(testReview2, product.getReviews().get(0));

        product.removeReview(testReview2);
        assertEquals(new ArrayList<>(), product.getReviews());
    }
}
